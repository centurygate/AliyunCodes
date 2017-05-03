package com.xigeng.drainproject.service;

import com.xigeng.drainproject.model.AlarmItem;
import com.xigeng.drainproject.model.Measure;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/22.
 */

@Service("alarmManager")
public class AlarmManager {

    //15个监测数值的依次上限
    private double[] upper_bound= new double[15];

    //15个监测数值的依次下限
    private double[] lower_bound= new double[15];



    public AlarmManager(){
        upper_bound = new double[15];
        for(int i=0;i<15;i++){
            upper_bound[i] = 1.0;
        }

        lower_bound = new double[15];
        for (int i=0;i<15;i++){
            lower_bound[i] = 0.0;
        }
    }

    public double[] getUpper_bound(){ return upper_bound; }
    public void setUpper_bound(double[] bounds){
        if(bounds.length != 15)
        {
            return;
        }

        this.upper_bound = bounds;
    }


    public double[] getLower_bound(){ return lower_bound; }
    public void setLower_bound(double[] bounds){
        if (bounds.length != 15)
        {
            return;
        }

        this.lower_bound = bounds;
    }


    public List<AlarmItem> CheckRealtimeValues(List<Measure> values){

        List<AlarmItem> alarms = new ArrayList<AlarmItem>();

        if (values.size() != 15)
        {
            return alarms;
        }

        for (int i=0; i<values.size(); i++)
        {
            double val = values.get(i).getValue();
            if(val>=lower_bound[i] && val<=upper_bound[i])
            {
                continue;
            }

            //超出预警范围外，生成此条报警项，并附上所有的相关信息
            AlarmItem item = new AlarmItem();
            item.setItemid("报警项目XX");
            item.setDevice(values.get(i).getAssetname());
            item.setValue(values.get(i).getValue());

            Date nowTime=new Date();
            SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            item.setTime(time.format(nowTime));
            item.setCause("目前原因未明");

            alarms.add(item);
        }

        return alarms;
    }
}
