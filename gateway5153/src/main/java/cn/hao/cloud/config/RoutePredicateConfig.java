package cn.hao.cloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

/**
 * 路由断言匹配
 */
@Configuration
public class RoutePredicateConfig {

    /**
     * 请求发生在 2023-04-26 17:55:07 之后可以通过
     */
    @Bean
    public RouteLocator afterRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route("provider-predicate-after-practice",
                r -> r.path("/predicate/after").and().after(ZonedDateTime.parse("2023-04-26T17:55:07.409+08:00[Asia/Shanghai]"))
                        .uri("http://localhost:8008/")).build();
    }

    /**
     * 请求发生在 2023-04-26 17:55:07 之前可以通过
     */
    @Bean
    public RouteLocator beforeRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route("provider-predicate-before-practice",
                r -> r.path("/predicate/before/**").and().before(ZonedDateTime.parse("2023-04-26T17:55:07.409+08:00[Asia/Shanghai]"))
                        .uri("http://localhost:8008/")).build();
    }

    /**
     * 请求发生在 2023-04-26 17:55:07-2023-04-27 17:55:07 之间可以通过
     */
    @Bean
    public RouteLocator betweenRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route("provider-predicate-between-practice",
                r -> r.path("/predicate/between/**").and().between(ZonedDateTime.parse("2023-04-26T17:55:07.409+08:00[Asia/Shanghai]"), ZonedDateTime.parse("2023-04-27T17:55:07.409+08:00[Asia/Shanghai]"))
                        .uri("http://localhost:8008/")).build();
    }


    /**
     * 请求cookie携带name=zhangsan的可以通过
     */
    @Bean
    public RouteLocator cookieRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route("provider-predicate-cookie-practice",
                r -> r.path("/predicate/cookie/**").and().cookie("userName", "zhangsan")
                        .uri("http://localhost:8008/")).build();
    }

    /**
     * query断言有两种方式 一种是携带指定的参数名称 query(String param)
     * 另一种是携带指定的参数键值对 query(String param, String regex)
     * 请求参数携带userName参数名称，或者password=任意数字
     */
    @Bean
    public RouteLocator queryRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {
        return routeLocatorBuilder.routes().route("provider-predicate-query-practice",
                r -> r.path("/predicate/query/**").and().query("userName").or().query("password", "\\d+")
                        .uri("http://localhost:8008/")).build();
    }


    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
        System.out.println(ZonedDateTime.parse("2023-04-27T17:55:07.409+08:00[Asia/Shanghai]"));
    }
}
