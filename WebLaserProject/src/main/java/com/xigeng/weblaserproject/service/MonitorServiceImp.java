package com.xigeng.weblaserproject.service;

import com.xigeng.weblaserproject.dao.IMonitorDao;
import com.xigeng.weblaserproject.model.Monitor;
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
