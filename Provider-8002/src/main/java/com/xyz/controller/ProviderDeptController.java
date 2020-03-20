package com.xyz.controller;

import com.xyz.entity.Dept;
import com.xyz.service.ProviderDeptService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyz
 * @date    2020年2月20日
 * @decription  服务提供者的controller层
 */

@RestController
@Log4j2
public class ProviderDeptController {

    @Autowired
    private ProviderDeptService providerDeptService;

    @RequestMapping("/provider/list")
    public Dept list() {
        return providerDeptService.list();
    }

    /**
     * @description 模拟服务不返回
     */
    @RequestMapping("/zuul/retry")
    public String zuulRetry() throws InterruptedException {
        log.info("come info zuul retry ...");
        Thread.sleep(10000);
        return "retry sucess ..";
    }

}
