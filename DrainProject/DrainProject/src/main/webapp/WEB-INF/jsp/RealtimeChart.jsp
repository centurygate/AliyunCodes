<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/26
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Realtime chart</title>

    <!--Bootstrap系统-->
    <link href="${pageContext.request.contextPath}/static/css/bootstrap.min.css" rel="stylesheet"/>
    <link href="${pageContext.request.contextPath}/static/css/styles.css" rel="stylesheet"/>

    <link href="${pageContext.request.contextPath}/static/css/realtimecharts.css" rel="stylesheet"/>
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
        <li class="active">
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


<div class="col-lg-10 col-lg-offset-2 main">

    <div class="row" id="contentcrumb">
        <ol class="breadcrumb">
            <li><a href="#">
                <svg class="glyph stroked line-graph">
                    <use xlink:href="#stroked-line-graph"></use>
                </svg>
            </a></li>
            <li class="active">实时曲线</li>
        </ol>
    </div><!--/.row-->
    <hr/> <!--分割横线-->

    <div class="row">
        <div class="container">
            <!--2个比例的图表类型列表-->
            <div class="col-lg-2">
                <div class="sidebar-nav panel panel-default">
                    <div class="panel-heading">
                        <p class="panel-title">设备检测列表</p>
                    </div>
                    <div class="panel-body">

                        <!--液位计->设备1-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新鸿路(锡梅路北)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="0">
                                    <span class="chart-name">液位计1</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--液位计->设备2-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新华路泵站</div>
                            <div class="panel-body">
                                <div class="lyrow" name="1">
                                    <span class="chart-name">液位计2</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--液位计->设备3-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新洲路(锡梅路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="2">
                                    <span class="chart-name">液位计3</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--液位计->设备4-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">振华路(锡灵路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="3">
                                    <span class="chart-name">液位计4</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--液位计->设备5-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">珠江路(宝山路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="4">
                                    <span class="chart-name">液位计5</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <%--==============================================================================================--%>
                        <!--PH仪->设备1-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新鸿路(锡梅路北)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="5">
                                    <span class="chart-name">PH仪1</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--PH仪->设备2-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新华路泵站</div>
                            <div class="panel-body">
                                <div class="lyrow" name="6">
                                    <span class="chart-name">PH仪2</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--PH仪->设备3-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新洲路(锡梅路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="7">
                                    <span class="chart-name">PH仪3</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--PH仪->设备4-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">振华路(锡灵路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="8">
                                    <span class="chart-name">PH仪4</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--PH仪->设备5-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">珠江路(宝山路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="9">
                                    <span class="chart-name">PH仪5</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>


                        <%--==============================================================================================--%>
                        <!--氨氮仪->设备1-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新鸿路(锡梅路北)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="10">
                                    <span class="chart-name">氨氮仪1</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--氨氮仪->设备2-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新华路泵站</div>
                            <div class="panel-body">
                                <div class="lyrow" name="11">
                                    <span class="chart-name">氨氮仪2</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--氨氮仪->设备3-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">新洲路(锡梅路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="12">
                                    <span class="chart-name">氨氮仪3</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--氨氮仪->设备4-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">振华路(锡灵路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="13">
                                    <span class="chart-name">氨氮仪4</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!--氨氮仪->设备5-->
                        <div class="panel panel-warning">
                            <div class="panel-heading">珠江路(宝山路)</div>
                            <div class="panel-body">
                                <div class="lyrow" name="14">
                                    <span class="chart-name">氨氮仪5</span>
                                    <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                                    <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                                    <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                                    <div class="view">
                                        <div class="col-md-12 column" name="realtime">
                                            <!--<div id="main"></div>-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>
            </div>


            <!--10个比例的图表显示区-->
            <div class="col-lg-10">
                <div class="demo">
                    <%--固定存在的曲线对话框1--%>
                    <div class="lyrow ui-draggable" name="0" style="display: block;">
                        <span class="chart-name">液位计1</span>
                        <a href="#close" class="remove label label-danger"><i class="glyphicon-remove glyphicon"></i>删除</a>
                        <span class="drag label label-default"><i class="glyphicon glyphicon-move"></i>拖动</span>
                        <span class="transform label label-success"><i class="glyphicon glyphicon-retweet"></i>历史</span>
                        <div class="view">
                            <div class="col-md-12 column added" id="const-1" name="realtime">
                                <!--<div id="main"></div>-->
                            </div>
                        </div>
                    </div>


                    <div style="clear: both; "></div>
                </div>
            </div>
        </div>

    </div>


</div>    <!--/.main-->


<%--javascript--%>
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

<script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/scripts.min.js"></script>

</body>
</html>
