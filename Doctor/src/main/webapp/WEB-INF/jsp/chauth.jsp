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
    <title>权限更改</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"/>
    <%--<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/bootstrap-select.css"/>--%>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/app.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css/font-awesome.css"/>
    <%--<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-select.js"></script>--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.8.0.min.js"></script>
    <%--<!-- Latest compiled and minified CSS -->--%>
    <%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/css/bootstrap-select.min.css">--%>

    <%--<!-- Latest compiled and minified JavaScript -->--%>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/bootstrap-select.min.js"></script>--%>

    <%--<!-- (Optional) Latest compiled and minified JavaScript translation files -->--%>
    <%--<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.1/js/i18n/defaults-*.min.js"></script>--%>

<script type="text/javascript">
//
$(document).ready(function () {
    $('#sel').change(function () {
        alert("triger");
        console.log("triger");
        var username =  $("#sel").val();
        alert(username);
        var requesturi = "${pageContext.request.contextPath}/service/rest/getAuthByName?username=" + username;
        $.getJSON(requesturi, function(data){
            console.log(data);
            $(":checkbox[name='userauthorities']").prop("checked",false);
            var resource;
            for (resource in data)
            {
//                console.log(data[resource]);
//                console.log(data[resource].role+" : "+data[resource].url);
                  var tmpvalue = data[resource].url;
                $(":checkbox[value='"+tmpvalue+"']").prop("checked",true);

            }
        });
    });
});

    </script>
</head>

<body>
<div id="securityuserlist">
    <select id="sel">
        <option value="0">-请选择-</option>
        <c:forEach items="${securityUserEntityList}" var="securityUser">
            <option value="${securityUser.username}">${securityUser.username}</option>
        </c:forEach>
    </select>
</div>
<div id="authlist">
    <%--<p><input type="checkbox" name="category" value="今日话题" />今日话题 </p>--%>
    <c:forEach items="${securityResourceEntityList}" var="securityResource">
        <p><input type="checkbox" name="userauthorities" value="${securityResource.resString}"/>${securityResource.resString}</p>
    </c:forEach>
</div>
<br/>
<div>
    <input type="button" value="提交更改" id="changeauthority" class="btn btn-primary"/>
</div>
</body>
</html>