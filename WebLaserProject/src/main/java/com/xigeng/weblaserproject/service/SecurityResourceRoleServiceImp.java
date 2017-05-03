package com.xigeng.weblaserproject.service;

import com.xigeng.weblaserproject.dao.SecurityResourceRoleDao;
import com.xigeng.weblaserproject.model.SecurityResourceRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by free on 11/30/16.
 */

@Service("securityResourceRoleService")
public class SecurityResourceRoleServiceImp implements ISecurityResourceRoleService {

    @Autowired
    SecurityResourceRoleDao securityResourceRoleDao;


    public List<SecurityResourceRoleEntity> selectAllRoleIdbyResId(Long rescId) {
        return securityResourceRoleDao.selectAllRoleIdbyResId(rescId);
    }
}
