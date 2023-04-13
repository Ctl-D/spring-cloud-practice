package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerOrderApplication80 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOrderApplication80.class, args);
    }
}
