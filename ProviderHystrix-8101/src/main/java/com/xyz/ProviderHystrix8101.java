package com.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;




/**
 * @author xyz
 * @date 2020年2月20日
 * @description todo
 * @EnableEurekaClient  eureka client端开启注解，表示本服务启动后会注册到eureka server注册中心中
 * @EnableCircuitBreaker 开启对Hystrix熔断器的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
public class ProviderHystrix8101 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrix8101.class, args);
    }
}
