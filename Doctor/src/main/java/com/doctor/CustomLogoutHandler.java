package com.doctor;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by free on 16-11-17.
 */
public class CustomLogoutHandler implements LogoutHandler
{

	public CustomLogoutHandler() {
	}

	public void logout(HttpServletRequest request,
	                   HttpServletResponse response, Authentication authentication) {
		System.out.println("CustomLogoutSuccessHandler.logout() is called!");

	}

}
