package com.xigeng.weblaserproject.service;



import com.xigeng.weblaserproject.model.SecurityUserEntity;

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
