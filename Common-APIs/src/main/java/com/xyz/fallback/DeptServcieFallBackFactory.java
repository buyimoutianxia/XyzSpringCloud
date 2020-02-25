package com.xyz.fallback;

import com.xyz.entity.Dept;
import com.xyz.service.DeptService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author xyz
 * @date 2020年2月25日
 * @description 通过接口的方法实现fallback，与业务逻辑代码解耦,并减少重复代码量
 */
@Component
public class DeptServcieFallBackFactory implements FallbackFactory<DeptService> {

    @Override
    public DeptService create(Throwable throwable) {
        return new DeptService() {
            @Override
            public Dept list() {
                return new Dept().setDeptNo(9999)
                                 .setDeptName("fallbackfactory_name")
                                 .setDeptDesc("fackbackfacotry_desc");
            }
        };
    }
}
