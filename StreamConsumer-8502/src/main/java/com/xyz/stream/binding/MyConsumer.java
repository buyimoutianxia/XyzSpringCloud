package com.xyz.stream.binding;

import com.xyz.stream.channel.MyProcess;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;

/**
 * @author xyz
 * @date 2020-04-19-10:29
 * @decription todo
 */
@EnableBinding(MyProcess.class)
public class MyConsumer {

    @StreamListener(MyProcess.MYINPUT)
    public void input(String message) {
        System.out.println("接收的消息：" + message);
    }

}