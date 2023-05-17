package cn.hao.cloud;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigCenterApplication5555 {
    protected static final Logger logger = LoggerFactory.getLogger(ConfigCenterApplication5555.class);

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(ConfigCenterApplication5555.class, args);
        ConfigurableEnvironment environment = context.getEnvironment();
        String[] activeProfiles = environment.getActiveProfiles();
        for (String activeProfile : activeProfiles) {
            logger.info("active profile：" + activeProfile);
        }
        String gitUri = environment.getProperty("spring.cloud.config.server.git.uri");
        String nativeSearchPath = environment.getProperty("spring.cloud.config.server.native.search-locations");
        logger.info("spring.cloud.config.server.git.uri value：" + gitUri);
        logger.info("spring.cloud.config.server.native.search-locations value：" + nativeSearchPath);
    }
}
