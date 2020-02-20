package com.xyz.controller;

import com.xyz.entity.Dept;
import com.xyz.service.ProviderDeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xyz
 * @date    2020年2月20日
 * @decription  todo
 */

@RestController
public class ProviderDeptController {

    @Autowired
    private ProviderDeptService providerDeptService;

    @RequestMapping("/provider/list")
    public Dept list() {
//        System.out.println("hello");
        return providerDeptService.list();
    }

}
