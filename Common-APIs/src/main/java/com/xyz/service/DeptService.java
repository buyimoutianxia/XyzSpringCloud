package com.xyz.service;

import com.xyz.entity.Dept;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xyz
 * @date 2020年2月24日
 * @decription feign调用的客户端接口
 */
@FeignClient(value = "microservice-provider")
@Service
public interface DeptService {

    @RequestMapping("/provider/list")
    Dept list();

}
