package cn.hao.cloud.controller;

import cn.hao.cloud.api.GatewayOrderApi;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("gateway/order")
public class OrderController {

    @Autowired
    private GatewayOrderApi gatewayOrderApi;

    @PostMapping("create")
    public Result<Payment> create(@RequestBody Payment payment) {
        return gatewayOrderApi.create(payment);
    }

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return gatewayOrderApi.get(id);
    }

}
