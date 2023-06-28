package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StreamMQConsumerApplication8803 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQConsumerApplication8803.class, args);
    }
}
