package com.xyz.dao;

import com.xyz.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author  xyz
 * @data    2020年2月20日
 * @description todo
 */
@Repository
public class ProviderDeptDao {

    Dept dept = new Dept();

    public Dept list() {
        dept.setDeptNo(8001).setDeptName("dept_8001_name").setDeptDesc("dept_8001_desc");
        return dept;
    }

}
