package com.xyz.service;

import com.xyz.dao.ProviderDeptDao;
import com.xyz.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xyz
 * @data 2020年2月20日
 * @decription todo
 */
@Service
public class ProviderDeptService {

    @Autowired
    private ProviderDeptDao providerDeptDao;

    public Dept list() {
        return providerDeptDao.list();
    }

}
