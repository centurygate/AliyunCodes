<%--
  Created by IntelliJ IDEA.
  User: free
  Date: 16-11-16
  Time: 下午2:33
  To change this template use File | Settings | File Templates.
--%>
<%@page language="java" contentType="text/html;charset=utf-8" pageEncoding="utf-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>Login page</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/font-awesome.css"/>
</head>

<body>
<div id="mainWrapper">
    <div class="login-Container">
        <div class="login-card">
            <div class="login-form">
                <c:url var="loginUrl" value="/login"/>
                <form action="${pageContext.request.contextPath}/login" method="post" class="form-horizontal">
                    <c:if test="${param.error != null}">
                        <div class="alert alert-danger">
                            <p style="font-size: 20px;">${param.error}</p>
                        </div>
                    </c:if>
                    <c:if test="${param.state != null}">
                        <div class="alert alert-danger">
                            <p style="font-size: 20px;">${param.state}</p>
                                <%--<p style="font-size: 16px;">${SPRING_SECURITY_LAST_EXCEPTION.message}</p>--%>
                            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
                                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                            </c:if>
                        </div>
                    </c:if>
                    <c:if test="${param.changepwd != null}">
                        <div class="alert alert-danger">
                            <p style="font-size: 20px;">${param.changepwd}</p>
                                <%--<p style="font-size: 16px;">${SPRING_SECURITY_LAST_EXCEPTION.message}</p>--%>
                            <c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">
                                <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                            </c:if>
                        </div>
                    </c:if>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                            <p>You have been logged out successfully.</p>
                        </div>
                    </c:if>
                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="username"><i class="fa fa-user"></i></label>
                        <input type="text" class="form-control" id="username" name="username"
                               placeholder="Enter Username" required/>
                    </div>
                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="password"><i class="fa fa-lock"></i></label>
                        <input type="password" class="form-control" id="password" name="password"
                               placeholder="EnterPassword" required/>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="form-actions">
                        <input type="submit" class="btn btn-block btn-primary btn-default" value="Login"/>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>