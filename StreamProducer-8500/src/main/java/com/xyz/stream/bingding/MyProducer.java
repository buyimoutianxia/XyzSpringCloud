package com.xyz.stream.bingding;

import com.xyz.stream.channel.MyProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

/**
 * @author xyz
 * @date 2020-04-17-11:43
 * @decription 模拟消息发送
 */

@EnableBinding(MyProcess.class)
public class MyProducer {

    @Autowired
    private MessageChannel myoutput;

    public void send(Object messsage) {
        myoutput.send(MessageBuilder.withPayload(messsage).build());
    }

}
