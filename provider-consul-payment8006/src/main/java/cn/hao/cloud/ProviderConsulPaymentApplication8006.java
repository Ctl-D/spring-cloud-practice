package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProviderConsulPaymentApplication8006 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderConsulPaymentApplication8006.class, args);
    }
}
