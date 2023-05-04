package cn.hao.cloud.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.PrefixPathGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 给请求添加前缀/filter/uri
 */
@Component
public class PrefixPathFilter implements GatewayFilter, Ordered {

    private final PrefixPathGatewayFilterFactory prefixPathGatewayFilterFactory;

    public PrefixPathFilter(PrefixPathGatewayFilterFactory prefixPathGatewayFilterFactory) {
        this.prefixPathGatewayFilterFactory = prefixPathGatewayFilterFactory;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        PrefixPathGatewayFilterFactory.Config config = new PrefixPathGatewayFilterFactory.Config();
        config.setPrefix("/filter");
        GatewayFilter apply = prefixPathGatewayFilterFactory.apply(config);
        return apply.filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 41;
    }
}
