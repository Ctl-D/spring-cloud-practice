package cn.hao.cloud.controller;

import cn.hao.cloud.service.PaymentService;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
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

@RestController
@RequestMapping("hystrix/openFeign/payment")
public class OpenFeignPaymentController {

    protected static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
        return getMethod(id);
    }

    @GetMapping("runtimeException")
    public Result runtimeException() {
        int a = 10 / 0;
        return ResultTool.success();
    }

    @GetMapping("responseInThreeSecondLater")
    public Result responseInThreeSecondLater() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        return ResultTool.success("msg return success");
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
