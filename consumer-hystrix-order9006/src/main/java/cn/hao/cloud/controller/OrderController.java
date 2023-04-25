package cn.hao.cloud.controller;

import cn.hao.cloud.api.HystrixOrderApi;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("order")
public class OrderController {

    @Autowired
    private HystrixOrderApi hystrixOrderApi;

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return hystrixOrderApi.get(id);
    }

    @HystrixCommand(fallbackMethod = "stopWait", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @GetMapping("oneSecondRequestGetThreeSecondResponse")
    public Result oneSecondRequestGetThreeSecondResponse() {
        return hystrixOrderApi.responseInThreeSecondLater();
    }


    @GetMapping("runtimeException")
    public Result runtimeException() {
        return hystrixOrderApi.runtimeException();
    }

    public Result stopWait() {
        return ResultTool.success("不再继续等待，(`へ´*)ノ");
    }
}
