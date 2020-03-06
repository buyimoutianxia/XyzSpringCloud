package com.xyz.turbine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.turbine.EnableTurbine;


/**
 * @author xyz
 * @date 2020年3月5日
 * @description Turbine主启动类
 * {@link EnableTurbine} 开启对Turbine的支持
 */
@SpringBootApplication
@EnableTurbine
public class TurbineApplication {

    public static void main(String[] args) {
        SpringApplication.run(TurbineApplication.class, args);
    }
}
