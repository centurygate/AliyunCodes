package com.xigeng.drainproject.dao;

import com.xigeng.drainproject.model.Monitor;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
public interface IMonitorDao {

    List<Monitor> selectAllMonitorList();
}
