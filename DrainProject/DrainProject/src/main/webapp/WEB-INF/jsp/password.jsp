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
    <title>密码更改</title>

    <!--Bootstrap系统-->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/bootstrap-theme.min.css" rel="stylesheet"/>
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
            <a href="${pageContext.request.contextPath}/goindex.action"><svg class="glyph stroked home"><use xlink:href="#stroked-home"></use></svg>首页</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/goasset.action"><svg class="glyph stroked calendar"><use xlink:href="#stroked-calendar"></use></svg>资产管理</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/gomonitor.action"><span class="glyphicon glyphicon-stats"></span>在线监测</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/gochart.action"><svg class="glyph stroked line-graph"><use xlink:href="#stroked-line-graph"></use></svg>实时曲线</a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/goalarm.action"><svg class="glyph stroked sound on"><use xlink:href="#stroked-sound-on"/></svg>消息警报
                <span class="badge" id="alarmid">0</span>
            </a>
        </li>
        <li>
            <a href="${pageContext.request.contextPath}/gosheet.action"><svg class="glyph stroked table"><use xlink:href="#stroked-table"></use></svg>工单管理</a>
        </li>
        <li class="active">
            <a href="${pageContext.request.contextPath}/gopassword.action"><svg class="glyph stroked lock"><use xlink:href="#stroked-lock"/></svg>密码更改</a>
        </li>
        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN')">
            <li>
                <a href="${pageContext.request.contextPath}/goauthority.action"><svg class="glyph stroked male user "><use xlink:href="#stroked-male-user"/></svg>用户管理</a>
            </li>
        </sec:authorize>
    </ul>
</div><!--/.sidebar-->


<div class="col-lg-10 col-lg-offset-2 main">

    <div class="row" id="contentcrumb">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked lock">
                    <use xlink:href="#stroked-lock"/>
                </svg>
            </a></li>
            <li class="active">密码修改</li>
        </ol>
    </div><!--/.row-->
    <hr/> <!--分割横线-->

    <div class="row"> <!--/.row 居中放置欢迎图片-->
        <div class="col-lg-4 col-lg-offset-4">
            <form action="${pageContext.request.contextPath}/changepasswordaction" method="post">
                <div class="input-group input-sm">
                    <label class="input-group-addon" for="originPassword">原始密码： <i class="fa fa-lock"></i></label>
                    <input type="password" class="form-control" id="originPassword" name="originPassword"
                           placeholder="原始密码" required>
                </div>

                <div class="input-group input-sm">
                    <label class="input-group-addon" for="newPassword">新  密 码&nbsp;： <i class="fa fa-lock"></i></label>
                    <input type="password" class="form-control" id="newPassword" name="newPassword"
                           placeholder="新密码" required>
                </div>

                <div class="input-group input-sm">
                    <label class="input-group-addon" for="confirmPassword">确认密码： <i class="fa fa-lock"></i></label>
                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword"
                           placeholder="确认新密码" required>
                </div>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                <div class="form-actions">
                    <input type="submit" class="btn btn-block btn-primary btn-default" value="修改密码">
                </div>
            </form>
        </div>
    </div>


</div>    <!--/.main-->


<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>

<!--Icons图标-->
<script src="${pageContext.request.contextPath}/static/js/lumino.glyphs.js"></script>

<%--全局自定义js文件--%>
<script src="${pageContext.request.contextPath}/static/js/global-define.js"></script>

<%--websocket脚本--%>
<script src="${pageContext.request.contextPath}/static/js/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/AlarmListener.js"></script>

</body>
</html>