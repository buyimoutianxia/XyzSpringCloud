package com.xyz.controller;


import com.xyz.entity.Dept;
import com.xyz.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xyz
 * @date 2020年2月24日
 * @description feign调用的controller层
 */
@RestController
public class ConsumerFeign {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/consumerfeign/list")
    public Dept list() {
        return deptService.list();
    }
}
