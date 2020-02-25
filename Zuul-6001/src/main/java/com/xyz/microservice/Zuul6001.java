package com.xyz.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;


/**
 * @author xyz
 * @date 2020年2月25日
 * @decription Zuul网关的主启动类
 * @EnableZuulProxy 开启对Zuul的支持
 */
@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class Zuul6001 {

    public static void main(String[] args) {
        SpringApplication.run(Zuul6001.class, args);
    }
}
