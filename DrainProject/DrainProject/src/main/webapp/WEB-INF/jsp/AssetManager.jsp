<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/28
  Time: 9:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Asset Manager</title>

    <!--Bootstrap系统-->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/project-main.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/asset-main.css" rel="stylesheet"/>


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
        <li class="active">
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
        <li>
            <a href="${pageContext.request.contextPath}/gopassword.action"><svg class="glyph stroked lock"><use xlink:href="#stroked-lock"/></svg>密码更改</a>
        </li>
        <sec:authorize access="hasAnyAuthority('ROLE_ADMIN')">
            <li>
                <a href="${pageContext.request.contextPath}/goauthority.action"><svg class="glyph stroked male user "><use xlink:href="#stroked-male-user"/></svg>用户管理</a>
            </li>
        </sec:authorize>
    </ul>
</div><!--/.sidebar-->


<div class="baidu-fullscreen">

    <div class="row" id="contentcrumb">
        <ol class="breadcrumb baidu-breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked calendar">
                    <use xlink:href="#stroked-calendar"></use>
                </svg>
            </a></li>
            <li class="active">资产管理</li>

            <%--右侧功能菜单--统计--%>
            <div class="menu-item" id="menu-figure">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    设施统计
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu pull-right" id="figurelist">
                    <li><a href="#" id="asset-count">安装数量</a></li>
                </ul>
            </div>

            <%--右侧功能菜单--查询--%>
            <div class="menu-item">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                    设施查询
                    <b class="caret"></b>
                </a>
                <ul class="dropdown-menu pull-right" id="querylist">
                    <li><a href="#" id="shuibeng">水泵</a></li>
                    <li class="divider"></li>
                    <li><a href="#" id="yeweiji">液位计</a></li>
                </ul>
            </div>

        </ol>
    </div>
    <hr style="margin: 0;"/>


    <%--设施统计的模态框Echart图表--%>
    <%--遮罩效果的模态框--%>
    <div class="modal fade" id="myModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        设施统计
                    </h4>
                </div>
                <div class="modal-body">
                    <div id="figure-chart">
                        <!--动态插入设施统计Echart图表-->
                    </div>
                </div>

            </div>
        </div>
    </div>


    <!--搜索列表实现-->
    <div class="searchlist" id="assetlist">

        <ul id="item-list">
            <img src="${pageContext.request.contextPath}/static/image/close1.png" id="close1"/>

            <li class="item-asset-null">
                <div class="tab"></div>
                <hr class="hr-style"/>
            </li>
        </ul>
    </div>

    <%--百度地图应用--%>
    <div class="baidu-asset" id="assetmap"></div>

</div>



<!--javascript 脚本-->
<script src="${pageContext.request.contextPath}/static/js/jquery-1.11.1.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>

<!--Icons图标-->
<script src="${pageContext.request.contextPath}/static/js/lumino.glyphs.js"></script>

<%--全局自定义js文件--%>
<script src="${pageContext.request.contextPath}/static/js/global-define.js"></script>

<%--websocket脚本--%>
<script src="${pageContext.request.contextPath}/static/js/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/AlarmListener.js"></script>


<script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
<script src="http://api.map.baidu.com/api?v=2.0&ak=U8GZ1pAUaHnQaoOvYAedyszjQHmIWAYD"></script>
<script src="${pageContext.request.contextPath}/static/js/AssetManager-main.js"></script>

</body>
</html>
