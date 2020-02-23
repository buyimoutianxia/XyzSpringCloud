package com.xyz.bean;


import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xyz
 * @date 2020年2月20日
 */
@Configuration
public class Config {

    /**
     * @author xyz
     * @date 2020年2月23日
     * @decription 修改ribbon默认算法
     */
    @Bean
    public IRule myRule() {
        return new RandomRule();
    }
}
