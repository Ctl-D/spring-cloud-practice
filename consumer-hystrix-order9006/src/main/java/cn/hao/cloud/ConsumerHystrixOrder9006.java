package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.hao.cloud.api")
@EnableCircuitBreaker
public class ConsumerHystrixOrder9006 {
    public static void main(String[] args) {
        SpringApplication.run(ConsumerHystrixOrder9006.class, args);
    }
}
