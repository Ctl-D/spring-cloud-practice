package cn.hao.cloud.controller;

import cn.hao.cloud.service.MessageProducer;
import cn.hao.common.cloud.tool.ResultTool;
import cn.hao.common.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageSendController {

    @Autowired
    private MessageProducer messageProducer;

    @GetMapping("send")
    public Result send() {
        messageProducer.send();
        return ResultTool.success();
    }

    @GetMapping("customChannelSend")
    public Result customChannelSend() {
        messageProducer.customChannelSend();
        return ResultTool.success();
    }

}
