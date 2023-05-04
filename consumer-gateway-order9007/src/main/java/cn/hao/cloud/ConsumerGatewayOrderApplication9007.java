package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.hao.cloud.api")
public class ConsumerGatewayOrderApplication9007 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerGatewayOrderApplication9007.class, args);
    }
}
