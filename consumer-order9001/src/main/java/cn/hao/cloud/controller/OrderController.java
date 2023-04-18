package cn.hao.cloud.controller;

import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping("order")
public class OrderController {

    @Value("${provider.service.url}")
    private String PROVIDER_SERVICE_URL;

    protected static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Resource
    private RestTemplate restTemplate;

    @PostMapping("create")
    public Result<Payment> create(@RequestBody Payment payment) {
        return restTemplate.postForObject(PROVIDER_SERVICE_URL + "payment/create", payment, Result.class);
    }

    @GetMapping("get/{id}")
    public Result<Payment> get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(PROVIDER_SERVICE_URL + "payment/get/"+id, Result.class);
    }

}
