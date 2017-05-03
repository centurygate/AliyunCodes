package com.doctor.mapping;

import com.doctor.model.SecurityResourceEntity;

import java.util.List;

public interface SecurityResourceEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SecurityResourceEntity record);

    int insertSelective(SecurityResourceEntity record);

    SecurityResourceEntity selectByPrimaryKey(Long id);

    List<SecurityResourceEntity> selectAllSecurityResource();
    int updateByPrimaryKeySelective(SecurityResourceEntity record);

    int updateByPrimaryKey(SecurityResourceEntity record);
}