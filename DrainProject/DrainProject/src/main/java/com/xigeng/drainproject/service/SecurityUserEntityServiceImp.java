package com.xigeng.drainproject.service;


import com.xigeng.drainproject.dao.SecurityUserEntityDao;
import com.xigeng.drainproject.model.SecurityUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by free on 11/26/16.
 */

@Service("securityUserEntityService")
public class SecurityUserEntityServiceImp implements SecurityUserEntityService {

    @Autowired
    private SecurityUserEntityDao securityUserEntityMapper;

    public SecurityUserEntity selectByPrimaryKey(Long id) {
        return securityUserEntityMapper.selectByPrimaryKey(id);
    }

    public int updateByPrimaryKey(SecurityUserEntity record) {
        return securityUserEntityMapper.updateByPrimaryKey(record);
    }

    public List<SecurityUserEntity> selectAllSecurityUserEntity() {
        return securityUserEntityMapper.selectAllSecurityUserEntity();
    }

    public SecurityUserEntity selectByUserName(String username) {
        return securityUserEntityMapper.selectByUserName(username);
    }
}
