package cn.hao.cloud.controller;

import cn.hao.cloud.api.EurekaOrderApi;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private EurekaOrderApi eurekaOrderApi;

    @PostMapping("create")
    public Result<Payment> create(@RequestBody Payment payment) {
        return eurekaOrderApi.create(payment);
    }

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return eurekaOrderApi.get(id);
    }

    @GetMapping("openFeignTimeout")
    public Result openFeignTimeout() {
        return eurekaOrderApi.openFeignTimeout();
    }

}
