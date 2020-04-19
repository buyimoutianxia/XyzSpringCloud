package com.xyz.stream.channel;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

/**
 * @author xyz
 * @date 2020-04-19-10:29
 * @decription todo
 */
public interface MyProcess {

    String MYOUTPUT = "myoutput";

    /**
     * 自定义消息生产者channel
     * @return {@link MessageChannel}
     */
    @Output("myoutput")
    MessageChannel myoutput();


    String MYINPUT = "myinput";

    /**
     * 自定义消息消费者channel
     * @return {@link SubscribableChannel}
     */
    @Input("myinput")
    SubscribableChannel myinput();
}

