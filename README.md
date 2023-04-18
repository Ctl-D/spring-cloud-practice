# spring-cloud-practice
个人听课跟练项目

### zookeeper服务注册遇到的问题
##### 1.pom中的zookeeper版本与服务器上的zookeeper版本问题
```java
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
远端服务器中的zookeeper版本3.7.1<br>
依赖中自带的zookeeper版本为zookeeper-3.5.3-beta.jar:3.5.3-beta-8ce24f9e675cbefffb8f21a47e06b42864475a60<br>
问题：在第一次启动服务是服务无法启动起来是因为依赖中自带的zookeeper版本与服务器不一致。<br>
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
