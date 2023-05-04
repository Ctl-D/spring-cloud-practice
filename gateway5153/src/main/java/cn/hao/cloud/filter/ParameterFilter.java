package cn.hao.cloud.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.AddRequestParameterGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


/**
 * 给请求添加参数uri?userName=lisi
 */
@Component
public class ParameterFilter implements GatewayFilter, Ordered {
    protected static final Logger logger = LoggerFactory.getLogger(ParameterFilter.class);

    private final AddRequestParameterGatewayFilterFactory addRequestParameterGatewayFilterFactory;

    public ParameterFilter(AddRequestParameterGatewayFilterFactory addRequestParameterGatewayFilterFactory) {
        this.addRequestParameterGatewayFilterFactory = addRequestParameterGatewayFilterFactory;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String[] nameValue = new String[]{"userName", "lisi"};
        AbstractNameValueGatewayFilterFactory.NameValueConfig nameValueConfig = new AbstractNameValueGatewayFilterFactory.NameValueConfig();
        nameValueConfig.setName(nameValue[0]);
        nameValueConfig.setValue(nameValue[1]);
        GatewayFilter apply = addRequestParameterGatewayFilterFactory.apply(nameValueConfig);
        return apply.filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        return 40;
    }

}
