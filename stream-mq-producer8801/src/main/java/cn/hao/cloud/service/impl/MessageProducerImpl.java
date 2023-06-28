package cn.hao.cloud.service.impl;

import cn.hao.cloud.channel.CustomChannel;
import cn.hao.cloud.service.MessageProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

@EnableBinding(value = {Source.class, CustomChannel.class})  //将信道channel与exchange绑定，Source定义推送消息的管道
public class MessageProducerImpl implements MessageProducer {
    protected static final Logger logger = LoggerFactory.getLogger(MessageProducerImpl.class);

    @Resource(name = Source.OUTPUT)
    private MessageChannel messageChannel;

    @Resource(name = CustomChannel.OUT_PUT)
    private MessageChannel customChannel;

    @Override
    public void send() {
        UUID uuid = UUID.randomUUID();
        //消费生产者服务构建一个Message对象，然后由Source通过定义的管道推送到mq
        messageChannel.send(MessageBuilder.withPayload(uuid).build());
        logger.info("**************-----------------uuid：{}-----------*******************", uuid);
    }

    @Override
    public void customChannelSend() {
        UUID uuid = UUID.randomUUID();
        Message<UUID> message = MessageBuilder.withPayload(uuid).build();
        customChannel.send(message);
    }
}
