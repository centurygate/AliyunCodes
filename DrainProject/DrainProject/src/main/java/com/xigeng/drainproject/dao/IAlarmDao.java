package com.xigeng.drainproject.dao;

import com.xigeng.drainproject.model.AlarmItem;

import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
public interface IAlarmDao {

    int insert(AlarmItem record);

    List<AlarmItem> selectAllItemList();

    void dropItem(String alarmname);

}
