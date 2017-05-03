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
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8">
    <title>用户管理</title>

    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap-select.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap-theme.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/jquery-confirm.min.css" rel="stylesheet"/>

    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/font-awesome.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/project-main.css" rel="stylesheet"/>

</head>

<body>


<%--规范样式-修改时连同个模块一起修改--%>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">

    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#sidebar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>

            <a class="navbar-brand" href="#"><span>XKEN</span>SYSTEM</a>

            <ul class="user-menu">
                <li class="dropdown pull-right">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        <svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg>
                        <sec:authentication property="principal.username"/>
                        <span class="caret"></span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li>
                            <a href="#"><svg class="glyph stroked male-user"><use xlink:href="#stroked-male-user"></use></svg>简介</a>
                        </li>
                        <li>
                            <a href="#"><svg class="glyph stroked gear"><use xlink:href="#stroked-gear"></use></svg>设置</a>
                        </li>
                        <li>
                            <a href="logout"><svg class="glyph stroked cancel"><use xlink:href="#stroked-cancel"></use></svg>退出</a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><!-- /.container-fluid -->
</nav>


<%--规范样式-修改时连同个模块一起修改--%>
<div class="col-lg-2 sidebar">

    <ul class="nav menu">
        <li>
            <a href="goindex.action"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg>首页</a>
        </li>
        <li>
            <a href="goasset.action"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg>资产管理</a>
        </li>
        <li>
            <a href="gomonitor.action"><span class="glyphicon glyphicon-stats"></span>在线监测</a>
        </li>
        <li>
            <a href="gochart.action"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>实时曲线</a>
        </li>
        <li>
            <a href="goalarm.action"><svg class="glyph stroked sound on"><use xlink:href="#stroked-sound-on"/></svg>消息警报
                <span class="badge" id="alarmid">0</span>
            </a>
        </li>
        <li>
            <a href="gosheet.action"><svg class="glyph stroked table"><use xlink:href="#stroked-table"></use></svg>工单管理</a>
        </li>
        <li>
            <a href="gopassword.action"><svg class="glyph stroked lock"><use xlink:href="#stroked-lock"/></svg>密码更改</a>
        </li>
        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN')">
            <li class="active">
                <a href="goauthority.action"><svg class="glyph stroked male user "><use xlink:href="#stroked-male-user"/></svg>用户管理</a>
            </li>
        </sec:authorize>
    </ul>
</div><!--/.sidebar-->


<div class="col-lg-10 col-lg-offset-2 main">
    <div class="row" id="contentcrumb">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked home">
                    <use xlink:href="#stroked-home"></use>
                </svg>
            </a></li>
            <li class="active">用户管理</li>
        </ol>
    </div><!--/.row-->
    <hr/> <!--分割横线-->
    <div class="row">
        <div class="col-lg-10 col-lg-offset-2 main">
            <div>
                <label for="sel"> 用户:&nbsp;&nbsp;&nbsp;&nbsp;</label>
                <select id="sel" class="selectpicker" data-live-search="true" title="-请选择用户-">
                    <c:forEach items="${securityUserEntityList}" var="securityUser">
                        <option value="${securityUser.id}">${securityUser.username}</option>
                    </c:forEach>
                </select>
            </div>
            </br>
            </br>
            <label for="authlist"> 权限列表:&nbsp;&nbsp;&nbsp;&nbsp;</label>
            <div id="authlist">
                <div class="row">
                    <c:forEach items="${securityResourceEntityList}" var="securityResource">
                        <p class="col-lg-6"><input type="checkbox" name="userauthorities"
                                                   value="${securityResource.id}"/>&nbsp;&nbsp;${securityResource.resString}
                        <p>
                    </c:forEach>
                </div>
            </div>
            <br/>
            <div>
                <input type="button" value="提交更改" id="changeauthority" class="btn btn-primary"/>
            </div>
        </div>
    </div>
</div>


<%--script脚本--%>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-select.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap-checkbox.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery-confirm.min.js"></script>

<!--Icons图标-->
<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/lumino.glyphs.js"></script>

<%--全局自定义js文件--%>
<script src="${pageContext.request.contextPath}/static/js/global-define.js"></script>

<%--websocket脚本--%>
<script src="${pageContext.request.contextPath}/static/js/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/AlarmListener.js"></script>

<script src="${pageContext.request.contextPath}/static/js/authority-main.js"></script>

</body>

</html>