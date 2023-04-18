package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConsumerOrderApplication9001 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOrderApplication9001.class, args);
    }
}
