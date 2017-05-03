/**
 * Created by Administrator on 2016/12/2.
 */

$(function () {

    var alarmcnt = null;

    //取数据库中现有的警报数目
    var alarmdataURL = project_url + "/serveralarmdata";
    $.ajax({
        url: alarmdataURL,
        type: "GET",
        success: function (data) {
            ajaxobj=eval("("+data+")");

            //更新badge徽章的值
            alarmcnt = ajaxobj.data.length;
            document.getElementById("alarmid").innerHTML = alarmcnt;
        }
    });


    //websocket接收消息
    //var stompClient = null;
    //var stompClient = null;
    var sockurl = project_url+'/add';

    var socket = new SockJS(sockurl);
    if((socket == undefined) || (socket == null))
    {
        console.log("(socket == undefined) || (socket == null)");
    }
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame){

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/alarm', function(calResult)
        {
            //取数据库中警报数据显示在徽章中，并且新添加的数据加1
            alarmcnt += 1;
            document.getElementById("alarmid").innerHTML = alarmcnt;
        });
    });
    
});