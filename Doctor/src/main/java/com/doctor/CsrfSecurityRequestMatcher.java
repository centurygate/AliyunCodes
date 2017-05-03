package com.doctor;

/**
 * Created by free on 16-11-17.
 */


import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;

public class CsrfSecurityRequestMatcher implements RequestMatcher
{
	private Pattern allowedMethods = Pattern
			.compile("^(GET|HEAD|TRACE|OPTIONS)$");

	public boolean matches(HttpServletRequest request)
	{
//		System.out.println("This request:"+request.getServletPath());
		if (execludeUrls != null && execludeUrls.size() > 0)
		{
			String servletPath = request.getServletPath();
			for (String url : execludeUrls)
			{
				if (servletPath.contains(url))
				{
					System.out.println("##################################################");
					System.out.println("Allow this request:"+servletPath);
					return false;
				}
			}
		}
		return !allowedMethods.matcher(request.getMethod()).matches();
	}

	/**
	 * 需要排除的url列表
	 */
	private List<String> execludeUrls;

	public List<String> getExecludeUrls()
	{
		return execludeUrls;
	}

	public void setExecludeUrls(List<String> execludeUrls)
	{
		this.execludeUrls = execludeUrls;
	}
}