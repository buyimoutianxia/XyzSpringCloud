package com.xyz.microservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author xyz
 * @date 2020年3月20日
 * @description  测试PreFilter的异常处理
 */

@Component
@Log4j2
public class MyPreFilterException extends ZuulFilter {

    private static final String PRE_TYPE = "pre";
    private static final int PRE_ORDER = -1;
    private static final boolean PRE_SHOULDFILTER = true;


    @Override
    public String filterType() {
        return  PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return PRE_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return PRE_SHOULDFILTER;
    }

    @Override
    public Object run() throws ZuulException {
        log.info("This is a pre filter");

        RequestContext ctx = RequestContext.getCurrentContext();
        try {
            myRuntimeException();
        }catch ( Exception e) {
            ctx.setResponseStatusCode(500);
//            ctx.setSendZuulResponse(false);
            ctx.setResponseBody("ResponseBody:this is my pre exception");
        }
        return null;
    }

    public void myRuntimeException() {
//        throw new RuntimeException("runtime exception...");  //PreFilter异常
        log.info("PreFilter do something");
    }
}
