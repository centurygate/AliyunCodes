package com.doctor;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by free on 2016/11/21.
 */

@Service
public class BusinessServiceImp implements IBusinessService {

    public List<User> getUser(String userinfo) {
        List<User> userList = null;
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class, User.class);
        try
        {
            userList =mapper.readValue(userinfo,javaType);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return userList;
    }

    public void SecurityMethodTest() {
        System.out.println("Enter SecurityMethodTest");
    }
}
