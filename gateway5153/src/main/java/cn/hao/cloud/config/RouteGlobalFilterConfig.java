package cn.hao.cloud.config;

import cn.hao.cloud.filter.global.CheckRequestParamFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteGlobalFilterConfig {

    @Bean
    public RouteLocator globalFilterRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("global-filter-route-practice", r -> r.path("/checkGlobalFilter/**")
                        .uri("http://localhost:8008/")).build();
    }

    @Bean
    public CheckRequestParamFilter getCheckRequestParamFilter() {
        return new CheckRequestParamFilter();
    }
}
