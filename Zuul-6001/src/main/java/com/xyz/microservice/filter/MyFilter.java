package com.xyz.microservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xyz
 * @date 2020年3月16日
 * @decription Zuul中自定义过滤器
 */

@Component
@Log4j2
public class MyFilter extends ZuulFilter {

    /**
     * 指定过滤器的类型，可以是pre/route/post/error
     * @return String
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     *  指定过滤器的优先级，数据越小，优先级越高
     * @return int
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     *  判断是否开启此过滤器，true-是，false-否
     * @return boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    /**
     * 过滤器的具体逻辑
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        Object accessToken = request.getParameter("accessToken");
        if( accessToken == null) {
            log.warn("access token is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            return null;
        }
        log.info("access token ok");
        return null;
    }
}
