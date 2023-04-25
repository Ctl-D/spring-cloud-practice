package cn.hao.cloud.api;

import cn.hao.cloud.api.impl.fallback.HystrixOrderApiFallbackImpl;
import cn.hao.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@FeignClient(name = "provider-payment-service", path = "hystrix/openFeign/payment", fallback = HystrixOrderApiFallbackImpl.class)
public interface HystrixOrderApi {

    @GetMapping("get/{id}")
    Result<Map<String, Object>> get(@PathVariable("id") Long id);

    @GetMapping("responseInThreeSecondLater")
    Result responseInThreeSecondLater();

    @GetMapping("runtimeException")
    Result runtimeException();

}
