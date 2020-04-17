package com.xyz.stream.bingding;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @author xyz
 * @date 2020-04-17-11:43
 * @decription 模拟消息发送
 */

@EnableBinding(Source.class)
public class MyProducer {

    @Autowired
    private MessageChannel output;

    public void send(Object messsage) {
        output.send(MessageBuilder.withPayload(messsage).build());
    }

}
