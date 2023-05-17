package cn.hao.cloud.api;

import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "provider-payment-service",path = "/payment")
public interface ConfigOrderApi {

    @PostMapping("create")
    Result<Payment> create(@RequestBody Payment payment);

    @GetMapping("get/{id}")
    Result<Map<String, Object>> get(@PathVariable("id") Long id);

    @GetMapping("openFeignTimeout")
    Result openFeignTimeout();

    @GetMapping("configChangeInfo")
    Result<Map<String, Object>> configChangeInfo();
}
