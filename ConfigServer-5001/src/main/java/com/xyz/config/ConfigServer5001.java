package com.xyz.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author xyz
 * @date 2020年2月28日
 * @description SpringCloud Config 配置中心服务端主启动类
 * {@link EnableConfigServer} 开启对Config Server的注解支持
 */

@SpringBootApplication
@EnableConfigServer
public class ConfigServer5001 {

    public static void main(String[] args) {
        SpringApplication.run(ConfigServer5001.class, args);
    }
}
