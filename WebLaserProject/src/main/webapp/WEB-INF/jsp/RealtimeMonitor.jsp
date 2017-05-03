<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/28
  Time: 10:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Realtime Monitor</title>

    <!--Bootstrap系统-->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet"/>

    <link href="${pageContext.request.contextPath}/static/css/project-main.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/monitor-main.css" rel="stylesheet"/>

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
        <li class="active">
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
            <li>
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
            <li class="active">在线监测</li>
        </ol>
    </div><!--/.row-->
    <hr/> <!--分割横线-->

    <div class="row">
        <div class="col-lg-2">
            <div class="panel panel-default" id="all-panel">
                <div class="panel-heading">
                    <p class="panel-title">设备列表</p>
                </div>
                <div class="panel-body">

                    <div class="panel panel-default" id="yewei-panel">
                        <div class="panel-heading">
                            <p class="panel-title">液位计(20)</p>
                        </div>
                        <div class="panel-body">

                            <!--设备1-->
                            <div class="panel panel-warning panel-first">
                                <div class="panel-heading">新鸿路(锡梅路北)</div>
                                <div class="panel-body">
                                    <p><span id="idtest"></span>meter</p>
                                </div>
                            </div>

                            <!--设备2-->
                            <div class="panel panel-warning panel-other">
                                <div class="panel-heading">新华路泵站上游</div>
                                <div class="panel-body">
                                    <p><span>0.481m</span> meter</p>
                                </div>
                            </div>

                            <!--设备3-->
                            <div class="panel panel-warning">
                                <div class="panel-heading">新洲路(锡梅路)</div>
                                <div class="panel-body">
                                    <p><span>2.976m</span> meter</p>
                                </div>
                            </div>


                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <p>一共22台设备</p>
                </div>
            </div>
        </div>

        <div class="col-lg-10" id="monitor-content">
            <div class="well">
                <div id="monitormap"></div>
            </div>
        </div>
    </div><!--/.row-->
</div>    <!--/.main-->


<!--javascript 脚本-->
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>

<!--Icons图标-->
<script src="${pageContext.request.contextPath}/static/js/lumino.glyphs.js"></script>

<%--全局自定义js文件--%>
<script src="${pageContext.request.contextPath}/static/js/global-define.js"></script>

<script src="${pageContext.request.contextPath}/static/js/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/RealtimeMonitor-main.js"></script>

<script src="http://api.map.baidu.com/api?v=2.0&ak=U8GZ1pAUaHnQaoOvYAedyszjQHmIWAYD"></script>

</body>
</html>