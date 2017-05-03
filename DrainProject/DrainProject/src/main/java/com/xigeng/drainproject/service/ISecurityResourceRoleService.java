package com.xigeng.drainproject.service;

import com.xigeng.drainproject.model.SecurityResourceRoleEntity;

import java.util.List;

/**
 * Created by free on 11/30/16.
 */
public interface ISecurityResourceRoleService {
    List<SecurityResourceRoleEntity> selectAllRoleIdbyResId(Long rescId);
}
