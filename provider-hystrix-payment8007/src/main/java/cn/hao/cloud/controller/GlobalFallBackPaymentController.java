package cn.hao.cloud.controller;

import cn.hao.cloud.service.PaymentService;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 全局降级配置，使用全局配置的时候，方法上依然要加@HystrixCommand，没有加的方法不会进行降级处理
 * 如果方法上@HystrixCommand中有指定的配置，降级处理则会就近原则，而不会使用全局的配置
 */
@DefaultProperties(defaultFallback = "globalBusinessToBusy")
@RestController
@RequestMapping("global/hystrix/payment")
public class GlobalFallBackPaymentController {
    protected static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return getMethod(id);
    }

    @HystrixCommand(commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @GetMapping("responseInThreeSecondLater")
    public Result responseInThreeSecondLater() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResultTool.success("msg return success");
    }

    @HystrixCommand(fallbackMethod = "businessToBusy")
    @GetMapping("runtimeException")
    public Result runtimeException() {
        int a = 10 / 0;
        return ResultTool.success();
    }

    @GetMapping("runtimeExceptionNoAnnotation")
    public Result runtimeExceptionNoAnnotation() {
        int a = 10 / 0;
        return ResultTool.success();
    }

    public Result<String> businessToBusy() {
        return ResultTool.error("业务繁忙o(╥﹏╥)o");
    }

    public Result<String> globalBusinessToBusy() {
        return ResultTool.error("全局业务繁忙o(╥﹏╥)o");
    }

    public Result<Map<String, Object>> getMethod(Long id) {
        Payment payment = paymentService.get(id);
        if (Objects.isNull(payment)) {
            logger.error("查询信息不存在");
            return ResultTool.error("信息不存在");
        }
        Map<String, Object> result = new HashMap<>();
        result.put("payment", payment);
        result.put("port", serverPort);
        return ResultTool.success(result);
    }
}
