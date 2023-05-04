# spring-cloud-practice
个人听课跟练项目

### zookeeper服务注册遇到的问题
##### 1.pom中的zookeeper版本与服务器上的zookeeper版本问题
```
子项目初始引入依赖，不用定义版本，由父级项目spring-cloud-dependencies约束
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
</dependency>
```
远端服务器中的zookeeper版本3.7.1
<br>
依赖中自带的zookeeper版本为zookeeper-3.5.3-beta.jar:3.5.3-beta-8ce24f9e675cbefffb8f21a47e06b42864475a60
<br>
问题：在第一次启动服务是服务无法启动起来是因为依赖中自带的zookeeper版本与服务器不一致。
<br>
<br>
**问题解决**
```
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-zookeeper-discovery</artifactId>
  <exclusions>
      <!--不引入自身携带的zookeeper-->
      <exclusion>
          <groupId>org.apache.zookeeper</groupId>
          <artifactId>zookeeper</artifactId>
      </exclusion>
  </exclusions>
</dependency>

<!--引入与使用zookeeper一样的的版本依赖-->
<dependency>
  <groupId>org.apache.zookeeper</groupId>
  <artifactId>zookeeper</artifactId>
  <version>3.7.1</version>
</dependency>
```
<br>

##### 2.服务注册到zookeeper连接超时
上述依赖问题解决后，服务可以正常启动了，但是在连接zookeeper一直超时
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/zookeeper-bulid/connection_zookeeper_time_out.png">
原因是因为服务器的防火墙是开启状态，拦截了外部对zookeeper端口的请求
<br>
解决方法：
* 1.关闭服务器的防火墙<br>
* 2.防火墙指定开放端口
```
关闭防火墙
systemctl stop firewalld

指定防火墙开放端口
firewall-cmd --add-port=2181/tcp --permanent
firewall-cmd --reload
```
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/zookeeper-bulid/firewall_add_zookeeper_port_result.png">
<br>
<br>

### consul服务注册与发现问题
consul依赖管理版本，不用定义版本，由父级项目spring-cloud-dependencies约束
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```
<br>

##### order模块调用payment模块失败
order调用payment服务是，系统报出没有可用的payment服务。
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/order_not_found_available_payemnt_service.png">
<br>
进入consul管理端，可以看到payment服务service健康状态并不是通过状态。
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/all_service_check_failing.png">
<br>
<br>
**问题解决**
<br>
网上找了很多，大部分都是添加spring.cloud.consul.discovery.health-check-url consul心跳检测地址，
通过查看源码ConsulDiscoverProperties，health-check-url已经有了默认值"/actuator/health"，如果没有特定需求的话，这个值不用修改。
<br>
最后部分博客说开启心跳检测机制 spring.cloud.consul.discovery.heartbeat.enable = true，修改后问题解决。
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/consul_heartbeat.default_value.png">
<br>
查询原因，查看当前引入的consul版本的源码，发现spring.cloud.consul.discovery.heartbeat.enable如果不设值心跳检测机制是关闭的。
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/resolve_all_service_check_failing_problem.png">
<br>
<br>

### OpenFeign服务调用
##### 在服务器熔断项目上练习出现的问题
###### **@FeignClients(name = "provider-payment-service")两个类上都是调用的同一个服务**
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/open-feign-build/feignClientSpecification_could_not_be_registered_error.png">
<br>
解决方式：配置文件上配置spring.main.allow-bean-definition-overriding=true
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/open-feign-build/feignClientSpecification_could_not_be_registered.png">
<br>

###### **@FeignClients的fallback属性不生效**
使其生效需要配置feign.hystrix.enable=true
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/open-feign-build/enable_hystrix_circuit_breaker.png">
<br>
<br>
### Hystrix服务降级熔断问题
首先子项目需要引入Hystrix的依赖，不用定义版本，由父级项目spring-cloud-dependencies约束
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```
##### 启用断路器两种方式
此处服务注入到了eurake
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/hystrix_project_use_annotation.png">
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/spring_cloud_annotation.png">
<br>
上述两种方式，都可以使断路器启用
<br>

##### 降级方法写法问题报错
###### **1.请求方法参数与fallback方法参数不一致**
此处举例为请求方法有参，fallback方法无参写法，
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/was_not_found_fallback_method_error_example.png">
<br>
错误信息：找不到对应方法
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/was_not_found_fallback_method_error.png">
<br>
###### **2.请求方法返参与fallback方法返参不一致**
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/fallback_method_incompatible_return_types_error_example.png">
<br>
错误信息：返回参数不兼容
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/fallback_method_incompatible_return_types.png">
<br>

###### **正确写法**
无参
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/no_parameter_request_fallback_method.png">
<br>
请求方法返回类型向下兼容
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/hystrix-build/return_types_downward_compatibility.png">
<br>
上述情况说明，请求方法和fallback方法的返回类型，请求参数需要一致。
<br>
<br>
### Gateway网关服务构建的问题
##### **关于配置文件中uri如何写**
能够确定只有一台服务时，此处以payment为例
<br>
```
spring:
  cloud:
    gateway:
      routes:
        - id: provider-payment-route #自定义服务对应的路由名称
          uri: localhost:8008/
          predicates:
            - Path=/gateway/payment/** #断言方式之一：匹配请求路径
```
当多台服务需要负载均衡时，首先需要开启从服务中心发现服务功能，其次需要uri以：lb://服务中心的对应服务名称格式书写 （lb相当于load balancer负载均衡）
<br>
```
spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true  #开启从服务注册中心获取服务动态生成路由
          lower-case-service-id: true # 服务名称转为小写
      routes:
        - id: provider-payment-route
          uri: lb://provider-payment-service/ #lb:// 负载均衡方式分配到指定服务 lb(load balancer)
          predicates:
            - Path=/gateway/payment/**
        - id: consumer-order-route
          uri: lb://consumer-order-service/
          predicates:
            - Path=/gateway/order/**

```
<br>

##### 修改响应体信息过滤器执行条件
自定义修改响应体内容的过滤器，核心就是配置ModifyResponseBodyGatewayFilterFactory的Config属性，在ModifyResponseBodyGatewayFilterFactory执行过滤器方法时，
会执行Config中的自定义RewriteFunction函数，下方就是配置ModifyResponseBodyGatewayFilterFactory.filter执行自定义修改响应体方法位置。
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/gateway-build/modify_response_body_function_apply_location.png">
<br>
<br>
**但是自定义修改响应体的过滤器必须在NettyWriteResponseFilter之后。**
<br>
<br>
**NettyWriteResponseFilter是一个Netty的过滤器，它的作用是在服务器端将响应数据写回到客户端之前进行一些操作。
<br>
具体来说，NettyWriteResponseFilter会在Netty的ChannelPipeline中注册一个ChannelOutboundHandler，在响应数据写回到客户端之前，通过这个Handler对响应数据进行拦截、修改或者其他处理。**
<br>
下方是writeWith方法实际开始调用执行位置
<br>
<img src="https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/gateway-build/wirteWith_method_apply_location.png">
<br>
