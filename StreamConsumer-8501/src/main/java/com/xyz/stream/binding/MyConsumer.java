package com.xyz.stream.binding;

import com.xyz.stream.channel.MyProcess;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author xyz
 * @date 2020-04-17-22:13
 * @decription 消息接收
 */

@EnableBinding(MyProcess.class)
public class MyConsumer {

    @StreamListener(MyProcess.MYINPUT)
    public void input(String message) {
        System.out.println("接收的消息：" + message);
    }

}
