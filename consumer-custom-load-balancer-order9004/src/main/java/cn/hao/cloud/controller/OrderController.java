package cn.hao.cloud.controller;

import cn.hao.common.cloud.lb.CustomLoadBalancer;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("order")
public class OrderController {

    @Value("${provider.service.name}")
    private String PROVIDER_SERVICE_NAME;

    protected static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private CustomLoadBalancer customLoadBalancer;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @PostMapping("create")
    public Result<Payment> create(@RequestBody Payment payment) {
        return restTemplate.postForObject(getUriV3() + "payment/create", payment, Result.class);
    }

    @GetMapping("get/{id}")
    public Result<Payment> get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(getUriV3() + "payment/get/" + id, Result.class);
    }

    private URI getUri() {
        List<ServiceInstance> instanceList = discoveryClient.getInstances(PROVIDER_SERVICE_NAME);
        if (CollectionUtils.isEmpty(instanceList)) {
            throw new RuntimeException("找不到服务器");
        }
        ServiceInstance serviceInstance = customLoadBalancer.instance(instanceList);
        return serviceInstance.getUri();
    }


    private URI getUriV2() {
        ServiceInstance serviceInstance = customLoadBalancer.instance(PROVIDER_SERVICE_NAME, discoveryClient);
        return serviceInstance.getUri();
    }

    private URI getUriV3() {
        ServiceInstance serviceInstance = customLoadBalancer.instance(PROVIDER_SERVICE_NAME);
        return serviceInstance.getUri();
    }
}
