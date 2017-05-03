package com.xigeng.waterfactory.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

class CalcInput
{
	int num1;
	int num2;

	public int getNum1()
	{
		return num1;
	}

	public void setNum1(int num1)
	{
		this.num1 = num1;
	}

	public int getNum2()
	{
		return num2;
	}

	public void setNum2(int num2)
	{
		this.num2 = num2;
	}
}
@Controller
public class WebSocketController
{


//	@MessageMapping("/add" )
//    @SendTo("/topic/showResult")
//    public Result addNum(CalcInput input) throws Exception {
//        System.out.println("I received message");
//        Thread.sleep(2000);
//        Result result = new Result(input.getNum1()+"+"+input.getNum2()+"="+(input.getNum1()+input.getNum2()));
//        return result;
//    }

	@SubscribeMapping("/topic/info")
	public Info subscribe() throws Exception
	{

		System.out.println("I received subscribe message");

		Info info = new Info(new Integer(1), "Subscribe Successfully");

		return info;
	}

	@RequestMapping("/start")
	public String start()
	{
		return "start";
	}
}

class MeasureInfo
{
	public MeasureInfo(Long time, Double value)
	{
		this.time = time;
		this.value = value;
	}

	private Long time;
	private Double value;

	public Long getTime()
	{
		return time;
	}

	public void setTime(Long time)
	{
		this.time = time;
	}

	public Double getValue()
	{
		return value;
	}

	public void setValue(Double value)
	{
		this.value = value;
	}

	@Override
	public String toString()
	{
		Date date = new Date();
		date.setTime(time);
		return "MeasureInfo{" +
				"time=" + date +
				", value=" + value +
				'}';
	}
}

class Info
{
	public Integer getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(Integer statusCode)
	{
		this.statusCode = statusCode;
	}

	public String getStatusDesc()
	{
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc)
	{
		this.statusDesc = statusDesc;
	}

	private Integer statusCode;
	private String statusDesc;

	public Info(Integer statusCode, String statusDesc)
	{
		this.statusCode = statusCode;
		this.statusDesc = statusDesc;
	}

	@Override
	public String toString()
	{
		return "Info{" +
				"statusCode=" + statusCode +
				", statusDesc='" + statusDesc + '\'' +
				'}';
	}
}