package cn.hao.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ProviderConfigPaymentApplication8011 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderConfigPaymentApplication8011.class, args);
    }
}
