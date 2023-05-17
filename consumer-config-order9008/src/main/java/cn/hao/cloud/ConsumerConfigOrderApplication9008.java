package cn.hao.cloud;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "cn.hao.cloud.api")
public class ConsumerConfigOrderApplication9008 {

    protected static final Logger logger = LoggerFactory.getLogger(ConsumerConfigOrderApplication9008.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(ConsumerConfigOrderApplication9008.class, args);

        ConfigurableEnvironment environment = run.getEnvironment();
        logger.info("provider.service.url------------------------{}", environment.getProperty("provider.service.url"));

    }
}
