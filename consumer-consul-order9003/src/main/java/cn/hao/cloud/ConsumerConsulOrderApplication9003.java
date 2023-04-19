package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerConsulOrderApplication9003 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerConsulOrderApplication9003.class, args);
    }
}
