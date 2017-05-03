package com.xigeng.drainproject.dao;



import com.xigeng.drainproject.model.SecurityUserEntity;

import java.util.List;

public interface SecurityUserEntityDao {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityUserEntity record);

    int insertSelective(SecurityUserEntity record);

    SecurityUserEntity selectByPrimaryKey(Long id);

    SecurityUserEntity selectByUserName(String username);

    List<SecurityUserEntity> selectAllSecurityUserEntity();

    int updateByPrimaryKeySelective(SecurityUserEntity record);

    int updateByPrimaryKey(SecurityUserEntity record);

//    int updatePasswordByPrimaryKey(Long id);
}