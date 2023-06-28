package cn.hao.cloud.channel;

import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface CustomChannel {

    String OUT_PUT = "custom-output-channel";

    @Output(OUT_PUT)
    MessageChannel output();
}
