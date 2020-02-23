package com.xyz.bean;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xyz
 * @date 2020年2月20日
 */
@Configuration
public class Config {

    @Bean
    /**
     * ribbon基于客户端的负载均衡工具
     */
    @LoadBalanced
    public RestTemplate myRestTemplate() {
        return new RestTemplate();
    }


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
