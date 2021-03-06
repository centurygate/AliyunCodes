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
    <title>Sheet Manager</title>

    <!--Bootstrap系统-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/styles.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-theme.min.css"/>

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
        <li class="active">
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
            <li class="active">工单管理</li>
        </ol>
    </div><!--/.row-->
    <hr/> <!--分割横线-->

    <div class="row">
        <div class="col-lg-12">
            <button type="button" class="btn btn-primary" id="btn-add" data-toggle="modal" data-target="#myModal"> 添加
            </button>
            <button type="button" class="btn btn-danger" id="btn-multidel"> 批量删除</button>
        </div>
    </div>

    <%--遮罩效果的模态框--%>
    <div class="modal fade" id="myModal" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                    <h4 class="modal-title" id="myModalLabel">
                        标题
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="form-addsheet" role="form" action="doaddsheet" method="post">
                        <div class="form-group">
                            <label class="col-lg-3  control-label">工单名称</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="sheetname">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">所属部门</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="department">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">设备名称</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="assetname">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">开始时间</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="starttime">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">运行状态</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" name="runstate">
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" type="button"
                            onclick="document.getElementById('form-addsheet').submit();">保存
                    </button>
                </div>
            </div>
        </div>
    </div>

    <%--第二个模态框供修改使用--%>
    <div class="modal fade" id="myModal2" role="dialog" aria-hidden="true" aria-labelledby="myModalLabel2">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button class="close" aria-hidden="true" type="button" data-dismiss="modal">×</button>
                    <h4 class="modal-title" id="myModalLabel2">
                        标题
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" id="form-addsheet2" role="form" action="dosheetedit" method="post">
                        <div class="form-group">
                            <label class="col-lg-3  control-label">工单名称</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" id="idname" name="sheetname">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">所属部门</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" id="iddepartment" name="department">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">设备名称</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" id="idasset" name="assetname">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">开始时间</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" id="idtime" name="starttime">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-lg-3 control-label">运行状态</label>
                            <div class="col-lg-7">
                                <input type="text" class="form-control" id="idstate" name="runstate">
                            </div>
                        </div>
                    </form>

                </div>
                <div class="modal-footer">
                    <button class="btn btn-default" type="button" data-dismiss="modal">关闭</button>
                    <button class="btn btn-primary" type="button"
                            onclick="document.getElementById('form-addsheet2').submit();">保存
                    </button>
                </div>
            </div>
        </div>
    </div>
    <br>

    <div class="row">
        <div class="col-lg-12">

            <table class="table" id="sheettable">
                <thead>
                <tr>
                    <th>工单名称</th>
                    <th>所属部门</th>
                    <th>设备名称</th>
                    <th>开始时间</th>
                    <th>状态</th>
                    <th>基本操作</th>
                </tr>
                </thead>

                <tbody>
                <tr>
                    <%--这里放置表格内容--%>
                </tr>
                </tbody>
            </table>

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

<%--websocket脚本--%>
<script src="${pageContext.request.contextPath}/static/js/sockjs.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/stomp.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/AlarmListener.js"></script>

<script src="${pageContext.request.contextPath}/static/js/SheetManager-main.js"></script>

</body>
</html>
