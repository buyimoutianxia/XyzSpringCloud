package com.xyz.gateway.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xyz
 * @date 2020-04-15-17:22
 * @decription 自定义限流规则
 */

@Configuration
@Log4j2
public class KeyResolverConfiguration {

    /**
     * 基于请求路径的限流规则
     */
    @Bean
    public KeyResolver pathKeyResolver() {
        return new KeyResolver() {
            @Override
            public Mono<String> resolve(ServerWebExchange exchange) {
                return Mono.just(exchange.getRequest().getPath().toString());
            }
        };
    }


    /**
     *  基于请求参数限流
     */
    @Bean
    public KeyResolver userKeyResolver() {
        return  exchange -> Mono.just(
            exchange.getRequest().getQueryParams().getFirst("userId")
        );
    }
}
