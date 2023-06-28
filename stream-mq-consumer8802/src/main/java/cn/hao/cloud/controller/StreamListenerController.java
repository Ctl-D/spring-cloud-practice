package cn.hao.cloud.controller;


import cn.hao.cloud.channel.CustomChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(value = {Sink.class, CustomChannel.class})
public class StreamListenerController {

    protected static final Logger logger = LoggerFactory.getLogger(StreamListenerController.class);

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void receive(Message<String> message) {
        String payload = message.getPayload();
        logger.info("Sink listener port：{}，接收的消息：{}", serverPort, payload);
    }

    @StreamListener(CustomChannel.IN_PUT)
    public void customChannelReceive(Message<String> message) {
        String payload = message.getPayload();
        logger.info("CustomChannel listener port：{}，接收的消息：{}", serverPort, payload);
    }


}
