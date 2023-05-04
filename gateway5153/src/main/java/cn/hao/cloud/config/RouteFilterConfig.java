package cn.hao.cloud.config;

import cn.hao.cloud.filter.ModifyResponseBodyFilter;
import cn.hao.cloud.filter.ParameterFilter;
import cn.hao.cloud.filter.PrefixPathFilter;
import org.springframework.cloud.gateway.filter.factory.AddRequestParameterGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteFilterConfig {

    @Bean
    public RouteLocator parameterFilterRoute(RouteLocatorBuilder builder) {
        return builder.routes().route("provider-filter-parameter-practice",
                r -> r.path("/filter/addRequestParameter/**").uri("http://localhost:8008")
                        .filters(new ParameterFilter(new AddRequestParameterGatewayFilterFactory()))
        ).build();
    }

    @Bean
    public RouteLocator prefixPathFilterRoute(RouteLocatorBuilder builder) {
        return builder.routes().route("provider-filter-prefixPath-practice",
                r -> r.path("/prefixPath/**").uri("http://localhost:8008")
                        .filters(new PrefixPathFilter(new PrefixPathGatewayFilterFactory()))
        ).build();
    }

    @Bean
    public RouteLocator modifyResponseBodyFilterRoute(RouteLocatorBuilder builder) {
        return builder.routes().route("provider-filter-modifyResponseBody-practice",
                r -> r.path("/filter/modifyResponseBody/**").uri("http://localhost:8008")
                        .filters(new ModifyResponseBodyFilter(new ModifyResponseBodyGatewayFilterFactory(null)))
        ).build();
    }
}
