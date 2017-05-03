package com.xigeng.waterfactory.controller;

import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * Created by free on 16-11-14.
 */
public interface SecurityUsersMapper
{
	@Select("SELECT * FROM security_user")
	List<SecurityUser> getUserList();
}
