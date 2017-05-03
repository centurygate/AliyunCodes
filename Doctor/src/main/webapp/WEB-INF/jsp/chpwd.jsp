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
                <form action="${pageContext.request.contextPath}/changepasswordaction" method="post"
                      class="form-horizontal">
                    <%--<c:if test="${param.error != null}">--%>
                    <%--<div class="alert alert-danger">--%>
                    <%--<p style="font-size: 20px;">${param.error}</p>--%>
                    <%--</div>--%>
                    <%--</c:if>--%>
                    <%--<c:if test="${param.state != null}">--%>
                    <%--<div class="alert alert-danger">--%>
                    <%--<p style="font-size: 20px;">${param.state}</p>--%>
                    <%--&lt;%&ndash;<p style="font-size: 16px;">${SPRING_SECURITY_LAST_EXCEPTION.message}</p>&ndash;%&gt;--%>
                    <%--<c:if test="${not empty SPRING_SECURITY_LAST_EXCEPTION }">--%>
                    <%--<c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>--%>
                    <%--</c:if>--%>
                    <%--</div>--%>
                    <%--</c:if>--%>
                    <c:if test="${param.logout != null}">
                        <div class="alert alert-success">
                            <p>You have been logged out successfully.</p>
                        </div>
                    </c:if>
                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="originPassword"><i class="fa fa-lock"></i></label>
                        <input type="password" class="form-control" id="originPassword" name="originPassword"
                               placeholder="Enter OriginPassword" required>
                    </div>

                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="newPassword"> <i class="fa fa-lock"></i></label>
                        <input type="password" class="form-control" id="newPassword" name="newPassword"
                               placeholder="Enter NewPassword" required>
                    </div>

                    <div class="input-group input-sm">
                        <label class="input-group-addon" for="confirmPassword"> <i class="fa fa-lock"></i></label>
                        <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                               placeholder="Enter ConfirmPassword" required>
                    </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="form-actions">
                        <input type="submit" class="btn btn-block btn-primary btn-default" value="ChangePassword">
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>