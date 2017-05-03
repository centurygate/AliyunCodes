package com.xigeng.drainproject.service;

import com.xigeng.drainproject.dao.IMonitorDao;
import com.xigeng.drainproject.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/8.
 */
@Service("monitorService")
public class MonitorServiceImp implements IMonitorService {

    @Autowired
    private IMonitorDao monitorDao;

    public List<Monitor> selectAllMonitorList(){
        return monitorDao.selectAllMonitorList();
    }
}
