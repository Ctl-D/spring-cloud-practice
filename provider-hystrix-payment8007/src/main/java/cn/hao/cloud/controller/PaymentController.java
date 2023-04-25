package cn.hao.cloud.controller;

import cn.hao.cloud.service.PaymentService;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/hystrix/payment")
@SpringCloudApplication
public class PaymentController {

    protected static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return getMethod(id);
    }

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

    public Result<String> businessToBusy() {
        return ResultTool.error("业务繁忙o(╥﹏╥)o");
    }

    @HystrixCommand(fallbackMethod = "businessToBusy", commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
    })
    @GetMapping("responseInThreeSecondLater/{id}")
    public Result responseInThreeSecondLater(@PathVariable("id") Long id) throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return getMethod(id);
    }

    public Result<String> businessToBusy(Long id) {
        return ResultTool.error("业务繁忙o(╥﹏╥)o");
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


    /**
     *
     * 10秒内如果错误率达到60% 则会启动熔断机制短路，后续请求就算请求正确，也会进行降级处理
     *
     */
    @HystrixCommand(fallbackMethod = "businessToBusy", commandProperties = {
            //开启熔断机制
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //熔断器在一个窗口时间内最小的请求量
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),
            //熔断器的在短路后尝试恢复的休眠时间
            //在休眠时间内，Hystrix断路器会定期尝试允许被短路的命令的一个请求，
            //以检测它是否已经可以正常工作。如果该请求成功执行，则 Hystrix 断路器将关闭并重置，
            //该命令的请求将开始正常执行。如果该请求仍然失败，则 Hystrix 断路器将继续保持开启状态，等待下一次休眠时间的到来。
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),
            //熔断器开启条件，在一个窗口时间内错误的请求占最小请求量占比 达到这个占比则短路
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60")
    })
    @GetMapping("enableCircuitBreaker/{id}")
    public Result enableCircuitBreaker(@PathVariable("id") Long id) {
        if (id < 0) {
            throw new RuntimeException("参数小于0");
        }
        return ResultTool.success(id);
    }

}
