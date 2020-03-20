package com.xyz.microservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import sun.misc.Request;

/**
 * @author xyz
 * @date 2020年3月20日
 * @description 测试PostFilter的异常处理
 */
@Component
@Log4j2
public class MyPostFilterException extends ZuulFilter {

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return -1;
    }

    @Override
    public boolean shouldFilter() {
        return  true;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("come in postfilter ....");

        RequestContext ctx = RequestContext.getCurrentContext();

        try {
            myPostFilterException();
        } catch (Exception e) {
            ctx.setResponseStatusCode(500);
            ctx.setResponseBody("ResponseBody:My PostFilter Exception ...");
            e.printStackTrace();
        }
        return null;
    }

    private void myPostFilterException() {
        throw new RuntimeException("My PostFilter Exception");
    }
}
