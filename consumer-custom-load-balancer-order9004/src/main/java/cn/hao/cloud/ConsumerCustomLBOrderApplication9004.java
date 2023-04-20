package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(value = "cn.hao.**.cloud.*")
public class ConsumerCustomLBOrderApplication9004 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerCustomLBOrderApplication9004.class, args);
    }
}
