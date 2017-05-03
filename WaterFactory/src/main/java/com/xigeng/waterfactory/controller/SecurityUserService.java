package com.xigeng.waterfactory.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by free on 16-11-14.
 */

@Service("securityuserservice")
public class SecurityUserService implements ISecurityUserService
{
	@Autowired
	private SecurityUsersMapper securityUsersMapper;

	public List<SecurityUser> getUserList()
	{
		return this.securityUsersMapper.getUserList();
	}
}
