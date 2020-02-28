package com.xyz.configclient.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyz
 * @date    2020年2月28日
 * @description 通过@value注解获取配置中心的配置信息，通过rest的方式展示给浏览器
 */

@RestController
public class ClientController {

    @Value("${server.port}")
    private int port;

    @Value("${spring.application.name}")
    private String name;

    @Value("${my.desc}")
    private String desc;

    @RequestMapping("/config/client")
    public String rest() {
        return  "port:" + port + " ,name:" + name + " ,desc:" + desc;
    }


}
