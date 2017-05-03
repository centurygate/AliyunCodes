<%--
  Created by IntelliJ IDEA.
  User: free
  Date: 16-11-16
  Time: 下午2:32
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <%--<link href="<%=basePath%>static/css/app.css" rel="stylesheet"  type="text/css"/>--%>
    <title>Insert title here</title>
</head>
<body>
Message:${message}
<a href="${pageContext.request.contextPath}/changepassword">ChangePassword</a>
<a href="${pageContext.request.contextPath}/changeauth">ChangeAuthority</a>
</body>
</html>
