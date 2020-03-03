package com.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


/**
 * @author xyz
 * @date 2020年2月20日
 * @description 服务提供者的朱启动类
 * @EnableEurekaClient注解:eureka client端开启注解，表示本服务启动后会注册到eureka server注册中心中
 */

@SpringBootApplication
@EnableEurekaClient
public class Provider8001 {

    public static void main(String[] args) {
        SpringApplication.run(Provider8001.class, args);
    }
}
