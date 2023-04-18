package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ConsumerOrderApplication9002 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOrderApplication9002.class, args);
    }
}
