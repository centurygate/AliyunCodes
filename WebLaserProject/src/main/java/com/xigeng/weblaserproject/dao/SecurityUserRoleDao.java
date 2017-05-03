package com.xigeng.weblaserproject.dao;


import com.xigeng.weblaserproject.model.SecurityUserRoleEntity;



public interface SecurityUserRoleDao {
    int deleteByPrimaryKey(SecurityUserRoleEntity key);

    int insert(SecurityUserRoleEntity record);

    int insertSelective(SecurityUserRoleEntity record);

}