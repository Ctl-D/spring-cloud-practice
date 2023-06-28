package cn.hao.cloud.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface CustomChannel {

    String IN_PUT = "custom-input-channel";

    @Input(IN_PUT)
    SubscribableChannel input();
}
