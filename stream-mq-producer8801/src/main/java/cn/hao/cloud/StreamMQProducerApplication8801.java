package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class StreamMQProducerApplication8801 {
    public static void main(String[] args) {
        SpringApplication.run(StreamMQProducerApplication8801.class, args);
    }
}
