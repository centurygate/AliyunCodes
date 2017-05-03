package com.xigeng.drainproject.controller;

/**
 * Created by free on 16-11-16.
 */


import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xigeng.drainproject.log.SystemLog;
import com.xigeng.drainproject.model.*;
import com.xigeng.drainproject.service.*;
import com.xigeng.drainproject.springsecurity.MyJdbcDaoImpl;
import com.xigeng.drainproject.springsecurity.MyPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.Authentication;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
public class HelloWorldController {

    @Autowired
    private IAlarmService alarmService;

    @Autowired
    private ISheetService sheetService;

    @Autowired
    private IMonitorService monitorService;

    @Autowired
    private IAssetService assetService;

    @Autowired
    private IBusinessService businessService;

    @Autowired
    private SecurityUserEntityService securityUserEntityService;

    @Autowired
    private SecurityResourceService securityResourceService;

    @Autowired
    private SimpMessageSendingOperations smso;

    @Autowired
    private ISecurityResourceRoleService securityResourceRoleService;

    @Autowired
    private ISecurityUserRoleService securityUserRoleService;

    @RequestMapping("/hello")
    public ModelAndView helloWorld() {
        String message = "你好,Programmer";
//		try{
//			message= URLEncoder.encode(message,"utf-8");
//		}
//		catch(Exception e)
//		{}
        SystemLog.log(message);

        System.out.print(securityUserEntityService.selectByPrimaryKey(Long.valueOf(1l)));
        return new ModelAndView("hellopage", "message", message);
    }



    @RequestMapping(value = "/service/rest/getAuthByName", method = RequestMethod.GET)
    @ResponseBody
    public Set<Resource> getAuthByName(HttpServletRequest request, HttpServletResponse response, @RequestParam String userid) {
        SystemLog.log("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        SystemLog.log(userid);
        String resourceQuery = " select sr.id, sr.res_string,sr.res_type\n" +
                "from security_resource sr\n" +
                "join security_resource_role srr\n" +
                "on sr.id=srr.resc_id\n" +
                "join security_user_role sur\n" +
                "on sur.role_id=srr.role_id\n" +
                "join security_user su\n" +
                "on su.id = sur.user_id\n" +
                "where sr.bsinglerole = 1 and su.id = ?\n" +
                "order by sr.priority";
        Set<Resource> resourceSet = new HashSet<Resource>();
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        DataSource dataSource = (DataSource) ctx.getBean("dataSource");
        ResourceMapping resourceMapping = new ResourceMapping(dataSource, resourceQuery);
        List<Resource> resourceList = resourceMapping.execute(new Object[]{Long.valueOf(userid,10)});
        for (Resource resource :
                resourceList) {
            resourceSet.add(resource);
        }
        SystemLog.log("--------------------Resource List size"+resourceList.size()+"---------------------");
        SystemLog.log(resourceList);
        SystemLog.log("--------------------Resource Set  size"+resourceSet.size()+"---------------------");
        SystemLog.log(resourceSet);
        return resourceSet;
    }

    @RequestMapping(value = "/innerchauth")
    public String innerchauth()
    {
        SystemLog.log("Enter=========================> innerchauth");
        return "innerchauth";
    }

    @RequestMapping(value = "/doinnerchanuth", method = RequestMethod.POST)
    @ResponseBody
    public Result doinnerchanuth(@RequestParam String userid, @RequestParam String delauth, @RequestParam String addauth) {
        SystemLog.log("---------------------------------------------------------");
        SystemLog.log("userid:"+userid);
        SystemLog.log("delauth:"+delauth);
        SystemLog.log("addauth:"+addauth);
        if (!delauth.equals("none"))
        {
            String [] delauth_array= delauth.split(",");
            for (int i=0;i < delauth_array.length;i++)
            {
                List<SecurityResourceRoleEntity> securityResourceRoleEntityList = securityResourceRoleService.selectAllRoleIdbyResId(Long.valueOf(delauth_array[i]));
                if (securityResourceRoleEntityList.size() >1)
                {
                    SystemLog.log("Found Greater than 1");
                    Result result = new Result(0,"Error");
                    return result;
                }
                securityUserRoleService.deleteByPrimaryKey(new SecurityUserRoleEntity(Long.valueOf(userid,10),Long.valueOf(securityResourceRoleEntityList.get(0).getRoleId().intValue())));
            }
        }
        if (!addauth.equals("none"))
        {
            String [] addauth_array= addauth.split(",");
            for (int i=0;i < addauth_array.length;i++)
            {
                List<SecurityResourceRoleEntity> securityResourceRoleEntityList = securityResourceRoleService.selectAllRoleIdbyResId(Long.valueOf(addauth_array[i]));
                if (securityResourceRoleEntityList.size() >1)
                {
                    SystemLog.log("Found Greater than 1");
                    Result result = new Result(0,"Error");
                    return result;
                }
                securityUserRoleService.insert(new SecurityUserRoleEntity(Long.valueOf(userid,10),Long.valueOf(securityResourceRoleEntityList.get(0).getRoleId().intValue())));
            }
        }

        Result result = new Result(1,"ok");
        return result;
    }

    @RequestMapping(value = "/changepasswordaction", method = RequestMethod.POST)
    public String changepasswordaction(HttpServletRequest request, HttpServletResponse response, @RequestParam String originPassword, @RequestParam String newPassword, @RequestParam String confirmPassword) throws Exception {
        WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
        MyPasswordEncoder myPasswordEncoder = (MyPasswordEncoder) ctx.getBean("passwordEncoder");
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SecurityUserEntity securityUserEntity = securityUserEntityService.selectByUserName(userDetails.getUsername());
        SystemLog.log("originPassword: " + originPassword);
        SystemLog.log("newPassword: " + newPassword);
        SystemLog.log("confirmPassword: " + confirmPassword);

        if (myPasswordEncoder.encodePassword(originPassword.trim(), userDetails.getUsername()).equals(userDetails.getPassword())) {
            SystemLog.log("OriginPassword is Right");
        }

        if (newPassword.trim().equals(confirmPassword.trim())) {
            SystemLog.log("originPassword.trim().equals(confirmPassword.trim())");

            String encryptPassword = myPasswordEncoder.encodePassword(newPassword, userDetails.getUsername());

            securityUserEntity.setPassword(encryptPassword);
            securityUserEntityService.updateByPrimaryKey(securityUserEntity);
        }
        MyJdbcDaoImpl myJdbcDao = (MyJdbcDaoImpl) ctx.getBean("userDetailsService");
        myJdbcDao.loadUserByUsername(userDetails.getUsername());
//		FactoryBean factoryBean = (FactoryBean) ctx.getBean("&filterUrlInvocationSecurityMetadataSource");
//		FilterInvocationSecurityMetadataSource fids = (FilterInvocationSecurityMetadataSource) factoryBean.getObject();
//		FilterSecurityInterceptor filter = (FilterSecurityInterceptor) ctx.getBean("urlSecurityInterceptor");
//		filter.setSecurityMetadataSource(fids);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?changepwd=Change Password Successfully";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        SystemLog.log("Enter /login");
        return "login";
    }

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String welcome() {
//		businessService.SecurityMethodTest();
        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";//You can redirect wherever you want, but generally it's a good practice to show login screen again.
    }


	@RequestMapping(value="/service/rest/putdata", method = RequestMethod.GET)
	@ResponseBody
	public Result putdata(@RequestParam String value)
	{
		SystemLog.log("Enter /service/rest/putdata");
		businessService.SecurityMethodTest();

		//List<User> userList = businessService.getUser(userinfo);
//		double val = businessService.getValue(value);

		SystemLog.log("Convert from string  to Object List, just as blow------------------>");
//		SystemLog.log(val);

//        //推送消息1：measureInfo消息
//		MeasureInfo measureInfo = null;
//		measureInfo =new MeasureInfo(new Long(Calendar.getInstance().getTime().getTime()),val);
//		SystemLog.log("send"+measureInfo+" to /topic/showResult");
//
//        //websocket向订阅“/topic/showResult”者推送消息
//		smso.convertAndSend("/topic/showResult",measureInfo);

//
//        //推送消息2：alarm消息
//        AlarmItem alarmitem = new AlarmItem();
//        alarmitem.setItemid("报警idX");
//        alarmitem.setDevice("水泵");
//        alarmitem.setValue(100.5);
//        alarmitem.setTime("2016-12-2 00:00:00");
//        alarmitem.setCause("某种原因引起的异常，不得而知");

//        SystemLog.log("send" + alarmitem + "to /topic/showResult");
//        SystemLog.log(new Long(Calendar.getInstance().getTime().getTime()));

        //向数据库保存一份 消息报警信息
//        alarmService.addAlarmItem(alarmitem);
        ObjectMapper mapper = new ObjectMapper();
        AlarmItem alarmitem = null;

        try
        {
            alarmitem =mapper.readValue(value,AlarmItem.class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        smso.convertAndSend("/topic/alarm", alarmitem);

		return new Result(Integer.valueOf(1),"success");
	}



    //////////////////////////////////////////////////////////////////////////////////////////////////


    /*jsp页面跳转*/
    @RequestMapping(value = "goindex")
    public String goindex() throws Exception {

        return "index";
    }

    @RequestMapping(value = "goasset")
    public String goasset() throws Exception {

        return "AssetManager";
    }

    @RequestMapping(value = "gomonitor")
    public String gomonitor() throws Exception {

        return "RealtimeMonitor";
    }


    @RequestMapping(value = "gochart")
    public String gochart() throws Exception {

        return "RealtimeChart";
    }

    @RequestMapping(value = "goalarm")
    public String goalarm() throws Exception{

        return "AlarmManager";
    }


    @RequestMapping(value = "gosheet")
    public String gosheet() throws Exception {

        return "SheetManager";
    }


    @RequestMapping(value = "gopassword", method = RequestMethod.GET)
    public String gopassword() {

        return "password";
    }


    @RequestMapping(value = "goauthority", method = RequestMethod.GET)
    public String goauthority(Model model) {

        model.addAttribute("securityUserEntityList", securityUserEntityService.selectAllSecurityUserEntity());
        model.addAttribute("securityResourceEntityList", securityResourceService.selectAllSecurityResource());
        return "authority";
    }


    @RequestMapping(value = "gouser")
    public String gouser() throws Exception {

        return "UserManager";
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////

    @RequestMapping(value = "serversheetdata")
    public ModelAndView handleRequest(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        //利用bean获取数据库的工单项
        List<SheetItem> items = this.sheetService.selectAllItemList();
        PrintWriter out = response.getWriter();

        out.println("{");
        out.println("\"data\":[");

        for (int i = 0; i < items.size(); i++) {
            SheetItem itemx = items.get(i);

            out.println("{");
            out.println("\"item\":\"" + itemx.getItem() + "\",");
            out.println("\"department\":\"" + itemx.getDepartment() + "\",");
            out.println("\"facility\":\"" + itemx.getFacility() + "\",");
            out.println("\"time\":\"" + itemx.getTime() + "\",");
            out.println("\"state\":\"" + itemx.getState() + "\"");
            out.println("}");
            if (i != items.size() - 1) {
                out.println(",");
            }
        }

        out.println("]");
        out.println("}");

        return null;
    }


    @RequestMapping(value = "serveralarmdata")
    public ModelAndView handleAlarmRequest(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        //利用bean获取数据库的工单项
        List<AlarmItem> items = this.alarmService.selectAllItemList();
        PrintWriter out = response.getWriter();

        out.println("{");
        out.println("\"data\":[");

        for (int i = 0; i < items.size(); i++) {
            AlarmItem itemx = items.get(i);

            out.println("{");
            out.println("\"itemid\":\"" + itemx.getItemid() + "\",");
            out.println("\"device\":\"" + itemx.getDevice() + "\",");
            out.println("\"value\":\"" + itemx.getValue() + "\",");
            out.println("\"time\":\"" + itemx.getTime() + "\",");
            out.println("\"cause\":\"" + itemx.getCause() + "\"");
            out.println("}");
            if (i != items.size() - 1) {
                out.println(",");
            }
        }

        out.println("]");
        out.println("}");

        return null;
    }


    @RequestMapping(value = "monitor")
    public ModelAndView monitor(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        List<Monitor> monitors = this.monitorService.selectAllMonitorList();
        PrintWriter out = response.getWriter();

        out.println("{");
        out.println("\"data\":[");

        for (int i = 0; i < monitors.size(); i++) {
            Monitor monitor = monitors.get(i);

            out.println("{");
            out.println("\"name\":\"" + monitor.getName() + "\",");
            out.println("\"state\":\"" + monitor.getState() + "\",");
            out.println("\"positionx\":\"" + monitor.getPositionx() + "\",");
            out.println("\"positiony\":\"" + monitor.getPositiony() + "\"");
            out.println("}");
            if (i != monitors.size() - 1) {
                out.println(",");
            }
        }

        out.println("]");
        out.println("}");

        return null;
    }


    @RequestMapping(value = "asset")
    public ModelAndView doasset(HttpServletResponse response) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        List<Asset> assets = this.assetService.selectAllAssetList();
        PrintWriter out = response.getWriter();

        out.println("{");
        out.println("\"data\":[");

        for (int i = 0; i < assets.size(); i++) {
            Asset assetx = assets.get(i);

            out.println("{");
            out.println("\"name\":\"" + assetx.getName() + "\",");
            out.println("\"type\":\"" + assetx.getType() + "\",");
            out.println("\"positionx\":\"" + assetx.getPositionx() + "\",");
            out.println("\"positiony\":\"" + assetx.getPositiony() + "\"");
            out.println("}");
            if (i != assets.size() - 1) {
                out.println(",");
            }
        }

        out.println("]");
        out.println("}");

        return null;
    }


    @RequestMapping(value = "doaddsheet")
    public String doaddsheet(@RequestParam String sheetname, @RequestParam String department, @RequestParam String assetname, @RequestParam String starttime,
                             @RequestParam String runstate) {

        //测试插入一条item
        SheetItem textitem = new SheetItem(sheetname, department, assetname, starttime, runstate);
        sheetService.addSheetItem(textitem);

        return "SheetManager";
    }


    @RequestMapping(value = "dosheetdelete")
    public String dosheetdelete(@RequestParam String name) {

        //删除选中的条目
        sheetService.deleteItem(name);

        return "SheetManager";
    }


    @RequestMapping(value = "dosheetedit")
    public String dosheetedit(@RequestParam String sheetname, @RequestParam String department, @RequestParam String assetname, @RequestParam String starttime, @RequestParam String runstate) {

        SheetItem textitem = new SheetItem(sheetname, department, assetname, starttime, runstate);
        sheetService.updateItem(textitem);

        return "SheetManager";
    }
}