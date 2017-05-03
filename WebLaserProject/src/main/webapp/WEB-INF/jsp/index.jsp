<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/12/7
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>Metro Measure</title>

    <script src="${pageContext.request.contextPath}/static/js2/jquery-1.11.2.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js2/jquery.datetimepicker.js"></script>
    <script src="${pageContext.request.contextPath}/static/js2/jquery-form.js"></script>

    <script src="${pageContext.request.contextPath}/static/js2/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js2/highcharts.js"></script>
    <script src="${pageContext.request.contextPath}/static/js2/hm.js"></script>

    <script src="${pageContext.request.contextPath}/static/js2/navbar.js"></script>
    <script src="${pageContext.request.contextPath}/static/js2/share.js"></script>


    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css2/bootstrap.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css2/jquery.datetimepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css2/myIndex.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css2/style.min.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/css2/slide_share.css"/>

    <style type="text/css">
      .gxBodyBack_1,.gxBodyBack_2,.gxBodyBack_3,.gxBodyBack_4,.gxBodyBack_5{}
      .shuju_right {width: 1240px;margin: 0 auto;}
    </style>



  </head>

  <body background="${pageContext.request.contextPath}/static/images2/metro.jpg" style="background-repeat:no-repeat;background-size:100% 100%;">

  <form action="http://www.tlink.io/user/saveUserconfig.htm" method="post">
    <div class="container-fluid index_header indexHea_prei">
      <!--右边部分-->
      <div class="shuju_right" id="_showdiv">

        <!--logo图标位置-->
        <div class="col-sm-12 gxTitle_img" style="height: 100px;">
          <%--<div class="pull-left1 PuIMG"><img src="${pageContext.request.contextPath}/static/images2/logo.jpg"></div>--%>
        </div>

        <div class="shujuR_divBig_2">
          <div class="shujuR_div_2" id="d_1">
            <iframe id="iframe_1" src="${pageContext.request.contextPath}/static/queryConfigValue.html" style="width:100%;height:100%;border:none;"></iframe>
          </div>
          <div class="shujuR_divSName">
            <a href="javascript:showhis(106917,12432);">下行78环20161020初始5432mm</a>
          </div>
        </div>
        <div class="shujuR_divBig_2" style="padding: 0; margin: 0;">
          <div class="shujuR_div_2" id="d_2" style="padding: 0; margin: 0;">
            <iframe id="iframe_2" src="${pageContext.request.contextPath}/static/queryConfigLineData.html" style="width:100%;height:100%;border:none; padding: 0; margin: 0;"></iframe>
          </div>
          <div class="shujuR_divSName">
            <a href="javascript:showhis(106917,12432);">下行78环20161020初始5432mm</a>
          </div>
        </div>


<%--        <div class="shujuR_divBig_2">
          <div class="shujuR_div_2" id="d_3">
            <iframe id="iframe_3" src="${pageContext.request.contextPath}/static/queryConfigValue2.html" style="width:100%;height:100%;border:none;"></iframe>
          </div>
          <div class="shujuR_divSName">
            <a href="javascript:showhis(106917,12432);">下行78环20161020初始5432mm</a>
          </div>
        </div>
        <div class="shujuR_divBig_2" style="padding: 0; margin: 0;">
          <div class="shujuR_div_2" id="d_4" style="padding: 0; margin: 0;">
            <iframe id="iframe_4" src="${pageContext.request.contextPath}/static/queryConfigLineData2.html" style="width:100%;height:100%;border:none; padding: 0; margin: 0;"></iframe>
          </div>
          <div class="shujuR_divSName">
            <a href="javascript:showhis(106917,12432);">下行78环20161020初始5432mm</a>
          </div>
        </div>--%>


      </div>
      <!--  选择数据流/创建账户  -->
    </div>

  </form>


<%--  <script type="text/javascript">
    function showhis(sensorId,deviceId){
      $("#_iframe").attr("src","/device/doMapHistory.htm?sensorId="+sensorId+"&deviceId="+deviceId);
      $("#history").modal("show");
    }
  </script>--%>


  </body>
</html>
