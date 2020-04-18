package com.xyz.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author xyz
 * @date 2020-04-17-23:01
 * @decription 自定义消息channel
 */
public interface MyProcess {

    /**
     * 自定义消息生产者channel
     */
    String MYOUTPUT = "myoutput";

    @Output("myoutput")
    MessageChannel myoutput();


    /**
     * 自定义消息消费者channel
     */
    String MYINPUT = "myinput";

    @Input("myinput")
    SubscribableChannel myinput();
}
