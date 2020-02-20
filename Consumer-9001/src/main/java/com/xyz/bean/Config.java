package com.xyz.bean;

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
    public RestTemplate myRestTemplate() {
        return new RestTemplate();
    }
}
