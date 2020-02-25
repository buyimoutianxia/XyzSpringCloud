package com.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author xyz
 * @date 2020年2月25日
 * @EnableFeignClients 开启client端对feign的调用
 * @EnableEurekaClient 实现eureka client向服务端的注册功能
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ConsumerFeign9101 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeign9101.class, args);
    }
}
