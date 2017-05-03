package com.xigeng.drainproject.service;

import com.xigeng.drainproject.model.Monitor;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public interface IMonitorService {

    List<Monitor> selectAllMonitorList();
}
