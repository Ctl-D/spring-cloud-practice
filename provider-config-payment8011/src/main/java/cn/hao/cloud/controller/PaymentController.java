package cn.hao.cloud.controller;

import cn.hao.cloud.service.PaymentService;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Payment;
import cn.hao.common.entity.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("payment")
@RefreshScope
public class PaymentController {

    protected static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("create")
    public Result<Payment> create(@RequestBody Payment payment) {
        paymentService.create(payment);
        return ResultTool.success(payment);
    }

    @GetMapping("get/{id}")
    public Result<Map<String, Object>> get(@PathVariable("id") Long id) {
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


    @Value("${change.info}")
    private String paymentConfigChangeInfo;

    @GetMapping("configChangeInfo")
    public Result<Map<String, Object>> configChangeInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("paymentConfigChangeInfo", paymentConfigChangeInfo);
        data.put("paymentPort", serverPort);
        return ResultTool.success(data);
    }
}
