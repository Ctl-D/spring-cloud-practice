package cn.hao.cloud.config;


import cn.hao.common.cloud.lb.CustomLoadBalancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebMvcConfig {

    @Bean
    public CustomLoadBalancer getCustomerLoadBalancer() {
        return new CustomLoadBalancer();
    }

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
