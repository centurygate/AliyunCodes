package com.xigeng.drainproject.service;



import com.xigeng.drainproject.model.SecurityUserEntity;

import java.util.List;

/**
 * Created by free on 11/26/16.
 */
public interface SecurityUserEntityService {
    SecurityUserEntity selectByPrimaryKey(Long id);
    SecurityUserEntity selectByUserName(String username);
    int updateByPrimaryKey(SecurityUserEntity record);
    List<SecurityUserEntity>  selectAllSecurityUserEntity();
}
