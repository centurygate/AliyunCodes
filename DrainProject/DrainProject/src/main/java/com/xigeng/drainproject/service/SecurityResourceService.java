package com.xigeng.drainproject.service;



import com.xigeng.drainproject.model.SecurityResourceEntity;

import java.util.List;

/**
 * Created by free on 2016/11/27.
 */
public interface SecurityResourceService {
    List<SecurityResourceEntity> selectAllSecurityResource();
}
