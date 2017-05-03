package com.xigeng.weblaserproject.service;


import com.xigeng.weblaserproject.dao.SecurityResourceEntityDao;
import com.xigeng.weblaserproject.model.SecurityResourceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by free on 2016/11/27.
 */

@Service("securityResourceService")
public class SecurityResourceServiceImp implements SecurityResourceService {

    @Autowired
    private SecurityResourceEntityDao securityResourceEntityMapper;
    public List<SecurityResourceEntity> selectAllSecurityResource() {
        return securityResourceEntityMapper.selectAllSecurityResource();
    }
}
