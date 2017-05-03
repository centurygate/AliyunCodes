package com.xigeng.weblaserproject.service;

import com.xigeng.weblaserproject.model.SecurityUserRoleEntity;

/**
 * Created by free on 11/30/16.
 */
public interface ISecurityUserRoleService {
    int deleteByPrimaryKey(SecurityUserRoleEntity key);

    int insert(SecurityUserRoleEntity record);
}
