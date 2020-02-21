package com.xyz.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author xyz
 * @date 2020年2月20日
 * @decription eureka server
 */

@SpringBootApplication
/**
 *eureka server服务器端启动类注解，接收其他微服务注册进来
 */
@EnableEurekaServer
public class EureKaServer7001 {

    public static void main(String[] args) {
        SpringApplication.run(EureKaServer7001.class, args);
    }

}
