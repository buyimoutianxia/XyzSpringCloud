package com.xyz.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xyz
 * @date 2020-04-14-18:56
 * @decription SpringCloud gateway 启动类
 * {@link EnableDiscoveryClient}开启eureka client的服务发现
 */

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServerApplication.class, args);
    }
}
