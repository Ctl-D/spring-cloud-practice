package cn.hao.cloud.controller;

import cn.hao.cloud.api.ConfigOrderApi;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("order")
@RefreshScope
public class OrderController {

    @Autowired
    private ConfigOrderApi configOrderApi;

    @PostMapping("create")
    public Result<Payment> create(@RequestBody Payment payment) {
        return configOrderApi.create(payment);
    }

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return configOrderApi.get(id);
    }


    @Value("${change.info}")
    private String consumerChangeInfo;

    @GetMapping("configChangeInfo")
    public Result<Map<String, Object>> configChangeInfo() {
        Result<Map<String, Object>> result = configOrderApi.configChangeInfo();
        Map<String, Object> data = result.getData();
        data.put("orderConfigChangeInfo", consumerChangeInfo);
        return result;
    }
}
