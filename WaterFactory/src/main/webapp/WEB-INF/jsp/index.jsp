<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <%--browse menu js--%>
    <script src="${pageContext.request.contextPath}/js/jquery-1.8.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/sockjs.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/stomp.min.js"></script>
    <script src="http://code.highcharts.com/highcharts.js"></script>
    <script type="text/javascript">
        function CustomChart(chartid) {
            var tempchartid = chartid;
            var json = {};
            //var self = this;
            var inner__addPoint = null;
            var chart = {
                type: 'line',
                animation: Highcharts.svg, // don't animate in IE < IE 10.
                marginRight: 10,
                events: {
                    load: function () {
                        // set up the updating of the chart each second
                        var series = this.series[0];
                        inner__addPoint = function(point,redraw,shift){series.addPoint(point,redraw,shift)};
                    }
                }
            };
            var title = {
                text: 'WebSocket 推送数据'
            };
            var xAxis = {
                type: 'datetime',
                tickPixelInterval: 10
            };
            var yAxis = {
                title: {
                    text: '液位计读数'
                },
                plotLines: [{
                    value: 0,
                    width: 1,
                    color: '#808080'
                }]
            };
            var tooltip = {
                formatter: function () {
                    return '<b>' + this.series.name + '</b><br/>' +
                            Highcharts.dateFormat('%Y-%m-%d %H:%M:%S', this.x) + '<br/>' +
                            Highcharts.numberFormat(this.y, 2);
                }
            };
            var plotOptions = {
                area: {
                    pointStart: 1940,
                    marker: {
                        enabled: false,
                        symbol: 'circle',
                        radius: 2,
                        states: {
                            hover: {
                                enabled: true
                            }
                        }
                    }
                }
            };
            var legend = {
                enabled: false
            };
            var exporting = {
                enabled: false
            };
            var series= [{
                name: 'Random data',
                data: (function () {
                    // generate an array of random data
                    var data = [];
                    return data;
                }())
            }];

            json.chart = chart;
            json.title = title;
            json.tooltip = tooltip;
            json.xAxis = xAxis;
            json.yAxis = yAxis;
            json.legend = legend;
            json.exporting = exporting;
            json.series = series;
            json.plotOptions = plotOptions;


            Highcharts.setOptions({
                global: {
                    useUTC: false
                }
            });

            var pointcnt  =0;
            return {
                addPoint:function (measureInfo) {

                    if(pointcnt >=20 )
                    {
                        console.log(measureInfo);
                        inner__addPoint([measureInfo.time,measureInfo.value],true,true);
                    }
                    else
                    {
                        console.log(measureInfo);
                        inner__addPoint([measureInfo.time,measureInfo.value],true,false);
                    }
                    pointcnt++;
                },
                init:function () {
                    console.log("Initializing Chart......");
                    console.log("tempcharid = "+tempchartid);
                    $(tempchartid).highcharts(json);
                }
            }
        }
        var i = 0;
        var customchart_arrary = [];
        for (i = 0;i <8 ;i++)
        {
            var chartid = '#container'+(i+1);
            console.log(chartid);
            customchart_arrary[i] = new CustomChart(chartid);
        }

        $(document).ready(function() {
            for (i = 0;i <8 ;i++)
            {
                customchart_arrary[i].init();
            }
        });
        var stompClient = null;

        function setConnected(connected) {
            document.getElementById('connect').disabled = connected;
            document.getElementById('disconnect').disabled = !connected;
            document.getElementById('calculationDiv').style.visibility = connected ? 'visible' : 'hidden';
            document.getElementById('calResponse').innerHTML = '';
        }

        function connect() {
            var socket = new SockJS('/WaterFactory/add');
            if((socket == undefined) || (socket == null))
            {
                console.log("(socket == undefined) || (socket == null)");
            }
            stompClient = Stomp.over(socket);
            stompClient.connect({}, function(frame) {
                setConnected(true);
                console.log('Connected: ' + frame);
                stompClient.subscribe('/topic/info', function(calResult){
                    console.log(calResult);
                });
                stompClient.subscribe('/topic/showResult', function(calResult){
                    var measureInfo = JSON.parse(calResult.body);
                    showResult(measureInfo)

                });
            });
        }

        function disconnect() {
            stompClient.disconnect();
            setConnected(false);
            console.log("Disconnected");
        }

        function sendNum() {
            var num1 = document.getElementById('num1').value;
            var num2 = document.getElementById('num2').value;
            alert("num1 = "+num1+"num2 = "+num2);
            stompClient.send("/calcApp/add", {}, JSON.stringify({ 'num1': num1, 'num2': num2 }));
        }

        function showResult(measureInfo) {

            for (var i = 0;i < customchart_arrary.length;i++)
            {
                customchart_arrary[i].addPoint(measureInfo);
            }
        }

    </script>

    <title>${pageContext.request.contextPath}</title>

</head>
<body>
<div style="border: dotted;background-color: #761c19;color: #c1e2b3">
    <sec:authentication property="authorities" var="authorities" scope="page"/>
    <c:forEach items="${authorities}" var="authority">
        ${authority.authority}
    </c:forEach>
</div>
<div>
    <button id="connect" onclick="connect();">Connect</button>
    <button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button><br/><br/>
</div>
<div id="calculationDiv">
    <label>Number One:</label><input type="text" id="num1" /><br/>
    <label>Number Two:</label><input type="text" id="num2" /><br/><br/>
    <button id="sendNum" onclick="sendNum();">Send to Add</button>
    <p id="calResponse"></p>
</div>

<div id="container1" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container2" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container3" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container4" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container5" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container6" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container7" style="width: 550px; height: 400px; margin: 0 auto"></div>
<div id="container8" style="width: 550px; height: 400px; margin: 0 auto"></div>

</body>
</html>
