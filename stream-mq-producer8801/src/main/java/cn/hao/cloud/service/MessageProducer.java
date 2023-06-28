package cn.hao.cloud.service;


public interface MessageProducer {

    void send();

    void customChannelSend();
}
