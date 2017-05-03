/**
 * Created by Administrator on 2016/12/5.
 */

var alarmdataURL = project_url + "/serveralarmdata";
$(function () {
    //websocket连接
    connect();

    $.ajax({
        url: alarmdataURL,
        type: "GET",
        success: function (data) {
            //ajaxobj=eval("("+data+")");
            var ajaxobj = JSON.parse(data);
            var ctable = document.getElementById("alarmtable");

            for(var i=0;i<ajaxobj.length;i++)
            {
                var newRow = ctable.insertRow(i+1);
                var col1 = newRow.insertCell(0);
                var col2 = newRow.insertCell(1);
                var col3 = newRow.insertCell(2);
                var col4 = newRow.insertCell(3);
                var col5 = newRow.insertCell(4);
                var col6 = newRow.insertCell(5); //操作

                col1.innerHTML = ajaxobj[i].itemid;
                col2.innerHTML = ajaxobj[i].device;
                col3.innerHTML = ajaxobj[i].value;
                col4.innerHTML = ajaxobj[i].time;
                col5.innerHTML = ajaxobj[i].cause;

                $(col6).html("<input class='btn btn-info btn-sm btn-edit' id='" + 'curedit'+parseInt(i) + "' type='button' value='处理' onclick='dohandle(this)'/>" + "&nbsp;" +
                    "<input class='btn btn-danger btn-sm btn-del' id='" + 'curdelete'+parseInt(i) + "' type='button' value='删除' onclick='dodelete(this)'/>");
            }

            //更新badge徽章的值
            document.getElementById("alarmid").innerHTML = ajaxobj.length;
        }
    });
});


//websocket接收消息
var stompClient = null;
var sockurl = project_url+'/add';
function connect() {
    var socket = new SockJS(sockurl);
    if((socket == undefined) || (socket == null))
    {
        console.log("(socket == undefined) || (socket == null)");
    }
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function(frame) {

        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/alarm', function(calResult)
        {

            var alarmitem = JSON.parse(calResult.body);
            //alert(alarminfo.message);

            //可能包括多个Alarm条目注意循环添加
            for(var i=0; i<alarmitem.length ;i++)
            {
                var ctable = document.getElementById("alarmtable");
                if(ctable != null)
                {
                    var newRow = ctable.insertRow(1);

                    var col1 = newRow.insertCell(0);
                    var col2 = newRow.insertCell(1);
                    var col3 = newRow.insertCell(2);
                    var col4 = newRow.insertCell(3);
                    var col5 = newRow.insertCell(4);
                    var col6 = newRow.insertCell(5); //操作

                    col1.innerHTML = alarmitem[i].itemid;
                    col2.innerHTML = alarmitem[i].device;
                    col3.innerHTML = alarmitem[i].value;
                    col4.innerHTML = alarmitem[i].time;
                    col5.innerHTML = alarmitem[i].cause;

                    $(col6).html("<input class='btn btn-info btn-sm btn-edit' id='" + 'addedit'+parseInt(i) + "' type='button' value='处理' onclick='dohandle(this)'/>" + "&nbsp;" +
                        "<input class='btn btn-danger btn-sm btn-del' id='" + 'adddelete'+parseInt(i) + "' type='button' value='删除' onclick='dodelete(this)'/>");

                    //更新badge的数字徽章
                    var badgenumber = document.getElementById("alarmid").innerHTML;
                    document.getElementById("alarmid").innerHTML = parseInt(badgenumber) + 1;

                    //pop up some alert
                    /*                if(alarmitem.value < 200)
                     {
                     Lobibox.notify('warning',{msg:calResult.body});
                     }
                     else
                     {
                     Lobibox.notify('error',{msg:calResult.body});
                     }*/
                }
            }
        });
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}


//删除操作执行
function dodelete(obj) {
    var tourl = document.getElementById(obj.id).parentNode.parentNode.cells(0).innerHTML;
    window.location.href = "doalarmdelete.action?name=" + tourl;
}