package cn.hao.cloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    @LoadBalanced //开启负载均衡 从eureka服务注册中心随机获取一个支付服务
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

}
