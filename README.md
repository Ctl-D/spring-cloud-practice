# spring-cloud-practice
个人听课跟练项目

### zookeeper服务注册遇到的问题
##### 1.pom中的zookeeper版本与服务器上的zookeeper版本问题
```
父模块
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-zookeeper-dependencies</artifactId>
  <version>2.2.0.RELEASE</version>
</dependency>

子项目初始引入依赖
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
<br>
问题解决
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

##### 2.服务注册到zookeeper连接超时
上述依赖问题解决后，服务可以正常启动了，但是在连接zookeeper一直超时
<br>
![](https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/zookeeper-bulid/connection_zookeeper_time_out.png)
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
![](https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/zookeeper-bulid/firewall_add_zookeeper_port_result.png)

### consul服务注册与发现问题
consul依赖管理版本
```
<dependency>
<groupId>org.springframework.cloud</groupId>
<artifactId>spring-cloud-consul-dependencies</artifactId>
<version>2.2.1.RELEASE</version>
</dependency>
```
##### order模块调用payment模块失败
order调用payment服务是，系统报出没有可用的payment服务
<br>
![](https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/order_not_found_available_payemnt_service.png)
进入consul管理端，可以看到payment服务service健康状态并不是通过状态
![](https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/all_service_check_failing.png)
<br>
<br>
问题解决
<br>
网上找了很多，大部分都是添加spring.cloud.consul.discovery.health-check-url consul心跳检测地址，
通过查看源码ConsulDiscoverProperties，health-check-url已经有了默认值"/actuator/health",如果没有特定需求的话，这个值不用修改。
<br>
最后部分博客说开启心跳检测机制 spring.cloud.consul.discovery.heartbeat.enable = true，修改后问题解决。
![](https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/consul_heartbeat.default_value.png)
<br>
查询原因，查看当前引入的consul版本的源码，发现spring.cloud.consul.discovery.heartbeat.enable如果不设值心跳检测机制是关闭的。
![](https://github.com/AntsUnderTheStars/spring-cloud-practice/blob/master/note-img/consul-build/resolve_all_service_check_failing_problem.png)
