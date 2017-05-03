package com.xigeng.weblaserproject.dao;

import com.xigeng.weblaserproject.model.SecurityResourceRoleEntity;

import java.util.List;

public interface SecurityResourceRoleDao {
    int deleteByPrimaryKey(SecurityResourceRoleEntity key);

    int insert(SecurityResourceRoleEntity record);

    int insertSelective(SecurityResourceRoleEntity record);

    List<SecurityResourceRoleEntity> selectAllRoleIdbyResId(Long rescId);
}