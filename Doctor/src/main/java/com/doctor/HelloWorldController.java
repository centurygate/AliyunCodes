package com.doctor;

/**
 * Created by free on 16-11-16.
 */

import com.doctor.model.SecurityUserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;


class Resource {
	private String url;
	private String role;

	public Resource(String url, String role) {
		this.url = url;
		this.role = role;
	}

	public String getUrl() {
		return url;
	}

	public String getRole() {
		return role;
	}
}

class ResourceMapping extends MappingSqlQuery {
	protected ResourceMapping(DataSource dataSource,
							  String resourceQuery) {
		super(dataSource, resourceQuery);
		this.declareParameter(new SqlParameter(Types.VARCHAR)); //sql中注入一个参数
		compile();
	}

	protected Object mapRow(ResultSet rs, int rownum)
			throws SQLException {
		String url = rs.getString(1);
		String role = rs.getString(2);
		Resource resource = new Resource(url, role);
		return resource;
	}
}

@Controller
public class HelloWorldController {

	@Autowired
	private IBusinessService businessService;

	@Autowired
	private SecurityUserEntityService securityUserEntityService;

	@Autowired
	private SecurityResourceService securityResourceService;

	@Autowired
	private SimpMessageSendingOperations smso;

	@RequestMapping("/hello")
	public ModelAndView helloWorld(){
		String message = "你好,Programmer";
//		try{
//			message= URLEncoder.encode(message,"utf-8");
//		}
//		catch(Exception e)
//		{}
		System.out.println(message);

		System.out.print(securityUserEntityService.selectByPrimaryKey(Long.valueOf(1l)));
		return new ModelAndView("hellopage","message",message);
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public String changepassword()
	{
		System.out.println("Enter changepassword");
		return "chpwd";
	}

	@RequestMapping(value = "/changeauth", method = RequestMethod.GET)
	public String changeauth(Model model)
	{
		System.out.println("Enter changeauth");
		model.addAttribute("securityUserEntityList",securityUserEntityService.selectAllSecurityUserEntity());
		model.addAttribute("securityResourceEntityList",securityResourceService.selectAllSecurityResource());
		return "chauth";
	}

	@RequestMapping(value="/service/rest/getAuthByName", method = RequestMethod.GET)
	@ResponseBody
	public List<Resource> getAuthByName(HttpServletRequest request, HttpServletResponse response, @RequestParam String username)
	{
		System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
		System.out.println(username);
		String resourceQuery = " select sr.res_string,sr.res_type\n" +
				"from security_resource sr\n"+
				"join security_resource_role srr\n" +
				"on sr.id=srr.resc_id\n" +
				"join security_user_role sur\n" +
				"on sur.role_id=srr.role_id\n" +
				"join security_user su\n"+
				"on su.id = sur.user_id\n"+
				"where su.username = ?\n"+
				"order by sr.priority";
		WebApplicationContext ctx  = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		DataSource dataSource = (DataSource)ctx.getBean("dataSource");
//		Collection<? extends GrantedAuthority>  authorities= myJdbcDao.loadUserByUsername(username).getAuthorities();
		ResourceMapping resourceMapping = new ResourceMapping(dataSource,resourceQuery);
		List<Resource> resourceList = resourceMapping.execute(new Object[]{username});
		return resourceList;
	}

	@RequestMapping(value = "/changepasswordaction", method = RequestMethod.POST)
	public String changepasswordaction(HttpServletRequest request,HttpServletResponse response,@RequestParam String originPassword,@RequestParam String newPassword,@RequestParam String confirmPassword) throws Exception {
		WebApplicationContext ctx  = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		MyPasswordEncoder myPasswordEncoder = (MyPasswordEncoder) ctx.getBean("passwordEncoder");
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityUserEntity securityUserEntity = securityUserEntityService.selectByUserName(userDetails.getUsername());
		System.out.println("originPassword: "+originPassword);
		System.out.println("newPassword: "+newPassword);
		System.out.println("confirmPassword: "+confirmPassword);

		if (myPasswordEncoder.encodePassword(originPassword.trim(),userDetails.getUsername()).equals(userDetails.getPassword()))
		{
			System.out.println("OriginPassword is Right");
		}

		if (newPassword.trim().equals(confirmPassword.trim()))
		{
			System.out.println("originPassword.trim().equals(confirmPassword.trim())");

			String encryptPassword = myPasswordEncoder.encodePassword(newPassword,userDetails.getUsername());

			securityUserEntity.setPassword(encryptPassword);
			securityUserEntityService.updateByPrimaryKey(securityUserEntity);
		}
		MyJdbcDaoImpl myJdbcDao = (MyJdbcDaoImpl)ctx.getBean("userDetailsService");
		myJdbcDao.loadUserByUsername(userDetails.getUsername());
//		FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterUrlInvocationSecurityMetadataSource");
//		FilterInvocationSecurityMetadataSource fids = (FilterInvocationSecurityMetadataSource) factoryBean.getObject();
//		FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("urlSecurityInterceptor");
//		filter.setSecurityMetadataSource(fids);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null)
		{
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?changepwd=Change Password Successfully";
	}

	@RequestMapping(value= "/login", method = RequestMethod.GET)
	public String loginPage() {
		System.out.println("Enter /login");
		return "login";
	}

	@RequestMapping(value= {"/","/welcome"}, method = RequestMethod.GET)
	public String welcome() {
//		businessService.SecurityMethodTest();
		return "welcome";
	}
	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
	}


	@RequestMapping(value="/service/rest/putdata", method = RequestMethod.GET)
	@ResponseBody
	public Result putdata(@RequestParam String userinfo)
	{
		System.out.println("Enter /service/rest/putdata");
		businessService.SecurityMethodTest();
		List<User> userList = businessService.getUser(userinfo);
		System.out.println("Convert from string  to Object List, just as blow------------------>");
		System.out.println(userList);

		MeasureInfo measureInfo = null;
		measureInfo =new MeasureInfo(new Long(Calendar.getInstance().getTime().getTime()),new Double(Math.random()));
		System.out.println("send"+measureInfo+" to /topic/showResult");
		smso.convertAndSend("/topic/showResult",measureInfo);
		return new Result(Integer.valueOf(1),"success");
	}

}