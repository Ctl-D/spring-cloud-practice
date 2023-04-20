package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "cn.hao.cloud.api")
public class ConsumerOFOrderApplication9005 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerOFOrderApplication9005.class, args);
    }
}
