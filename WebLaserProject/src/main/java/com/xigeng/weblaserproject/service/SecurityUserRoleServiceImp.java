package com.xigeng.weblaserproject.service;

import com.xigeng.weblaserproject.dao.SecurityUserRoleDao;
import com.xigeng.weblaserproject.model.SecurityUserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by free on 11/30/16.
 */

@Service("securityUserRoleService")
public class SecurityUserRoleServiceImp implements ISecurityUserRoleService {

    @Autowired
    private SecurityUserRoleDao securityUserRoleDao;

    public int deleteByPrimaryKey(SecurityUserRoleEntity key) {
        return securityUserRoleDao.deleteByPrimaryKey(key);
    }

    public int insert(SecurityUserRoleEntity record) {
        return securityUserRoleDao.insert(record);
    }
}
