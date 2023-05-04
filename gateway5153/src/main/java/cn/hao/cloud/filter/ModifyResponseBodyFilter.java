package cn.hao.cloud.filter;


import cn.hao.common.entity.Result;
import org.reactivestreams.Publisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory;
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 将返回结果转换为大写
 */
@Component
public class ModifyResponseBodyFilter implements GatewayFilter, Ordered {


    private ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory;

    public ModifyResponseBodyFilter(ModifyResponseBodyGatewayFilterFactory modifyResponseBodyGatewayFilterFactory) {
        this.modifyResponseBodyGatewayFilterFactory = modifyResponseBodyGatewayFilterFactory;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ModifyResponseBodyGatewayFilterFactory.Config config = new ModifyResponseBodyGatewayFilterFactory.Config();
        config.setInClass(Result.class);
        config.setOutClass(Result.class);
        config.setRewriteFunction(new RewriteResponseBody());
        GatewayFilter apply = modifyResponseBodyGatewayFilterFactory.apply(config);
        return apply.filter(exchange, chain);
    }

    @Override
    public int getOrder() {
        //当前过滤器必须在NettyWriteResponseFilter之前实现，自定义修改响应体方法执行方法是在 NettyWriteResponseFilter.filter中执行
        return NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1;
    }
}

class RewriteResponseBody implements RewriteFunction<Result<String>, Result<String>> {

    protected static final Logger logger = LoggerFactory.getLogger(RewriteResponseBody.class);

    @Override
    public Publisher<Result<String>> apply(ServerWebExchange exchange, Result<String> body) {
        //如果路由没有完成应该是请求过滤器执行
        if (!ServerWebExchangeUtils.isAlreadyRouted(exchange)) {

        } else {
            //响应处理
            responseHandle(exchange, body);
        }

        return Mono.just(body);
    }

    @Override
    public <V> BiFunction<ServerWebExchange, Result<String>, V> andThen(Function<? super Publisher<Result<String>>, ? extends V> after) {
        return RewriteFunction.super.andThen(after);
    }

    private void responseHandle(ServerWebExchange exchange, Result<String> body) {
        logger.info("响应返回信息大写前信息：{}", body.getData());
        body.setData(body.getData().toUpperCase(Locale.ROOT));
        logger.info("响应返回信息大写后信息：{}", body.getData());

    }
}
