package com.xyz.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
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

    /**
     * @HystrixCommand 调用list方法失败并抛出异常后，会自动调用@HystrixCommand中faccbackMethod指定的方法
     */
    @RequestMapping("/provider/list")
    @HystrixCommand(fallbackMethod = "processHystrix")
    public Dept list() {
        /**
         * return providerDeptService.list();
         */
        throw new RuntimeException("没有对应信息");
    }

    public Dept processHystrix() {
        return new Dept().setDeptNo(8101)
                         .setDeptName("providerHystrix_8101")
                         .setDeptDesc("Hystrix_desc_8101");
    }

}
