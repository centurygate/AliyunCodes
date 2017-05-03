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
            ajaxobj=eval("("+data+")");
            var ctable = document.getElementById("alarmtable");

            for(var i=0;i<ajaxobj.data.length;i++)
            {
                var newRow = ctable.insertRow(i+1);
                var col1 = newRow.insertCell(0);
                var col2 = newRow.insertCell(1);
                var col3 = newRow.insertCell(2);
                var col4 = newRow.insertCell(3);
                var col5 = newRow.insertCell(4);
                col1.innerHTML = ajaxobj.data[i].itemid;
                col2.innerHTML = ajaxobj.data[i].device;
                col3.innerHTML = ajaxobj.data[i].value;
                col4.innerHTML = ajaxobj.data[i].time;
                col5.innerHTML = ajaxobj.data[i].cause;
            }

            //更新badge徽章的值
            document.getElementById("alarmid").innerHTML = ajaxobj.data.length;
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

            var ctable = document.getElementById("alarmtable");
            if(ctable != null)
            {
                var newRow = ctable.insertRow(1);

                var col1 = newRow.insertCell(0);
                var col2 = newRow.insertCell(1);
                var col3 = newRow.insertCell(2);
                var col4 = newRow.insertCell(3);
                var col5 = newRow.insertCell(4);

                col1.innerHTML = alarmitem.itemid;
                col2.innerHTML = alarmitem.device;
                col3.innerHTML = alarmitem.value;
                col4.innerHTML = alarmitem.time;
                col5.innerHTML = alarmitem.cause;

                //更新badge的数字徽章
                var badgenumber = document.getElementById("alarmid").innerHTML;
                document.getElementById("alarmid").innerHTML = parseInt(badgenumber) + 1;

                //pop up some alert
                if(alarmitem.value < 200)
                {
                    Lobibox.notify('warning',{msg:calResult.body});
                }
                else
                {
                    Lobibox.notify('error',{msg:calResult.body});
                }
            }
        });
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}