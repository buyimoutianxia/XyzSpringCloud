package com.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
/**
 * 开启client端对feign的调用
 */
@EnableFeignClients
public class ConsumerFeign9101 {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerFeign9101.class, args);
    }
}
