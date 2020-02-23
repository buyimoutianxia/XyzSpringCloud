package com.xyz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xyz
 * @date    2020年2月20日
 */
@SpringBootApplication
@EnableEurekaClient
public class Consumer9001 {

    public static void main(String[] args) {
        SpringApplication.run(Consumer9001.class, args);
    }
}
