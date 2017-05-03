package com.xigeng.weblaserproject.service;



import com.xigeng.weblaserproject.model.SecurityResourceEntity;

import java.util.List;

/**
 * Created by free on 2016/11/27.
 */
public interface SecurityResourceService {
    List<SecurityResourceEntity> selectAllSecurityResource();
}
