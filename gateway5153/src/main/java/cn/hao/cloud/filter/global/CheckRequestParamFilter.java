package cn.hao.cloud.filter.global;

import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
public class CheckRequestParamFilter implements GlobalFilter, Ordered {

    protected static final Logger logger = LoggerFactory.getLogger(CheckRequestParamFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (exchange.getRequest().getURI().getPath().contains("checkGlobalFilter")) {
            String requestParam = exchange.getRequest().getQueryParams().getFirst("requestParam");
            if (StringUtils.isEmpty(requestParam)) {
                logger.error("Require parameter missing……");
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
                response.getHeaders().add("Content-Type", "application/json");
                Result<String> error = ResultTool.error("Require parameter missing……");
                byte[] bytes = JSON.toJSONString(error, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.DisableCircularReferenceDetect}).getBytes(StandardCharsets.UTF_8);
                DataBuffer dataBuffer = response.bufferFactory().wrap(bytes);
                return response.writeWith(Mono.just(dataBuffer));
            }
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
