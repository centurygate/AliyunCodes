package com.xigeng.waterfactory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Calendar;
/**
 * Created by free on 2016/5/30.
 */

@Controller
public class CenterController
{

    @Autowired
    private SimpMessageSendingOperations smso;

    @Autowired
    private ISecurityUserService securityuserservice;

    @RequestMapping(value = {"/websocket"}, method = RequestMethod.GET)
    public String websocket() throws InterruptedException {
        System.out.println(securityuserservice.getUserList());
        System.out.println("Received websocket");
        int i = 0;
        MeasureInfo measureInfo = null;
        while(i < 100) {
            Thread.sleep(2000);
            measureInfo =new MeasureInfo(new Long(Calendar.getInstance().getTime().getTime()),new Double(Math.random()));
            System.out.println("send"+measureInfo+" to /topic/showResult");
            smso.convertAndSend("/topic/showResult",measureInfo);
            i++;
        }
        return "index";
    }
}
