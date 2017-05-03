package com.xigeng.drainproject.service;



import java.util.List;
import com.xigeng.drainproject.model.User;

/**
 * Created by free on 2016/11/21.
 */
public interface IBusinessService {


    public void SecurityMethodTest();
    public List<User> getUser(String userinfo);

    public double getValue(String val);
}
