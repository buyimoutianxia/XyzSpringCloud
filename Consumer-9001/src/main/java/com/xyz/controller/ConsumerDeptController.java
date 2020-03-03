package com.xyz.controller;

import com.xyz.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * @author xyz
 * @date 2020年2月20日
 * @description 服务消费者的controller层
 */
@RestController
public class ConsumerDeptController {

//    private static final String  REST_URL_PREFIX = "http://localhost:8001";
    /**
     * ribbon使用微服务名称调用
     */
    private static final String  REST_URL_PREFIX = "http://microservice-provider";

    /**
     * 客户端的http请求抽象类,用于访问restful接口
     * get请求使用
     * @see RestTemplate#getForObject(java.net.URI, java.lang.Class)
     *
     * post请求使用 {@link RestTemplate#postForLocation(java.net.URI, java.lang.Object)}
     */
    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/list")
    public Dept list() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/provider/list", Dept.class);
    }

}
