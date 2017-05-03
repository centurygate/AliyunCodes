package com.xigeng.drainproject.dao;


import com.xigeng.drainproject.model.SecurityUserRoleEntity;



public interface SecurityUserRoleDao {
    int deleteByPrimaryKey(SecurityUserRoleEntity key);

    int insert(SecurityUserRoleEntity record);

    int insertSelective(SecurityUserRoleEntity record);

}