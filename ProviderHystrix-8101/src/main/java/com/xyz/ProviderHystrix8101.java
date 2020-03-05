package com.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;


/**
 * @author xyz
 * @date 2020年2月20日
 * @description Hystrix版的服务提供者的主启动类
 * {@link EnableEurekaClient}  eureka client端开启注解，表示本服务启动后会注册到eureka server注册中心中
 * {@link EnableCircuitBreaker} 开启对Hystrix熔断器的支持
 * {@link EnableHystrixDashboard} 开启对hystrix dashboard的支持
 */
@SpringBootApplication
@EnableEurekaClient
@EnableCircuitBreaker
@EnableHystrixDashboard
public class ProviderHystrix8101 {

    public static void main(String[] args) {
        SpringApplication.run(ProviderHystrix8101.class, args);
    }
}
