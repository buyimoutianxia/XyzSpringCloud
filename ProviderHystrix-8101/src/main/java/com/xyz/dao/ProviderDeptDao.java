package com.xyz.dao;

import com.xyz.entity.Dept;
import org.springframework.stereotype.Repository;

/**
 * @author  xyz
 * @data    2020年2月20日
 * @description 服务提供者的DAO层
 */
@Repository
public class ProviderDeptDao {

    Dept dept = new Dept();

    public Dept list() {
        dept.setDeptNo(8101).setDeptName("deptHystrix_8101_name").setDeptDesc("deptHystrix_8101_desc");
        return dept;
    }

}
