package com.xyz.microservice.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import sun.rmi.runtime.Log;

/**
 * @author xyz
 * @date 2020年3月20日
 * @description 测试自定义routefilter中的异常处理
 */

@Component
@Log4j2
public class MyRouteFilterException extends ZuulFilter {

    private static final String ROUTE_TYPE = "route";
    private static final int ROUTE_ORDER = -1;

    @Override
    public String filterType() {
        return ROUTE_TYPE;
    }

    @Override
    public int filterOrder() {
        return ROUTE_ORDER;
    }

    @Override
    public boolean shouldFilter() {
        return true ;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();

        try {
            myRouteException();
        } catch (Exception e) {
            ctx.setResponseStatusCode(500);
            ctx.setResponseBody("ResponseBody: MyRouteFilterException...");
            e.printStackTrace();
        }
        return null;
    }

    private void myRouteException() {
//        throw new RuntimeException("route filter exception ...");
        log.info("RouteFilter do something");
    }
}
