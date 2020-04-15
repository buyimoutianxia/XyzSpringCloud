package com.xyz.gateway.filters;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xyz
 * @date 2020-04-15-15:16
 * @decription 自定义全局过滤器
 */

@Component
@Log4j2
public class MyFilter  implements GlobalFilter, Ordered {

    /**
     * 认证鉴权：判断请求参数中是否包含access-token，若包含继续处理，若不包含直接返回
     */
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("自定义全局过滤器-MyFilter");

        String token = exchange.getRequest().getQueryParams().getFirst("access-token");
        if ( token == null) {
            log.info("没有登录");
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
