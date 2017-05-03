package com.doctor;

import com.doctor.model.SecurityResourceEntity;

import java.util.List;

/**
 * Created by free on 2016/11/27.
 */
public interface SecurityResourceService {
    List<SecurityResourceEntity> selectAllSecurityResource();
}
