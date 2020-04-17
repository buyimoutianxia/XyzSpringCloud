package com.xyz.stream.binding;

import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;

/**
 * @author xyz
 * @date 2020-04-17-22:13
 * @decription 消息接收
 */

@EnableBinding(Sink.class)
public class MyConsumer {

    @StreamListener(Sink.INPUT)
    public void input(String message) {
        System.out.println("接收的消息：" + message);
    }

}
