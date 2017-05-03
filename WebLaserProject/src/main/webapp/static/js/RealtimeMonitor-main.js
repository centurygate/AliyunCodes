/**
 * Created by Administrator on 2016/10/28.
 */

window.onload= function(){ 
    RunBaiduMap();
    alarmListen();
    connect(); //websocket连接
}



function RunBaiduMap(){
    // 百度地图API功能
    var map = new BMap.Map("monitormap");    // 创建Map实例
    var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM}
    map.addControl(new BMap.NavigationControl(opts));
    map.centerAndZoom(new BMap.Point(118.8006, 32.0480), 14);  // 初始化地图,设置中心点坐标和地图级别
    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP ]}));
    map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    /*定义一个自定义控件显示各种设备状态的颜色气泡列表*/
    // 定义一个控件类,即function
    function monitorControl(){
        // 默认停靠位置和偏移量
        this.defaultAnchor = BMAP_ANCHOR_TOP_LEFT;
        this.defaultOffset = new BMap.Size(50, 10);
    }

    // 通过JavaScript的prototype属性继承于BMap.Control
    monitorControl.prototype = new BMap.Control();

    // 自定义控件必须实现自己的initialize方法,并且将控件的DOM元素返回
    // 在本方法中创建个div元素作为控件的容器,并将其添加到地图容器中
    monitorControl.prototype.initialize = function(map){
        // 创建一个DOM元素
        var div = document.createElement("div");
        div.style.width= 450;
        div.style.height= 45;

        // 添加图片说明
        var bigImg = document.createElement("img");
        bigImg.src="static/image/customctrl.png";
        div.appendChild(bigImg);

        // 添加DOM元素到地图中
        map.getContainer().appendChild(div);
        // 将DOM元素返回
        return div;
    }

    // 创建控件
    var myMonitorCtrl = new monitorControl();
    // 添加到地图当中
    map.addControl(myMonitorCtrl);


    //使用Ajax取服务器端类型和位置信息，自动判断添加覆盖物图标
    var tempurl = project_url+"/monitor";
    $.ajax({
        url: tempurl,
        type: "GET",
        success: function (data) {
            ajaxobj=eval("("+data+")");

            for(var i=0;i<ajaxobj.data.length;i++)
            {
                var state = ajaxobj.data[i].state;
                var posx = ajaxobj.data[i].positionx;
                var posy = ajaxobj.data[i].positiony;

                var pt= new BMap.Point(posx,posy);
                var myIcon = null;
                if(state == 1)
                {
                    myIcon = new BMap.Icon("static/image/11.png", new BMap.Size(26,30));
                }
                else if(state == 2)
                {
                    myIcon = new BMap.Icon("static/image/22.png", new BMap.Size(26,30));
                }
                else if(state == 3)
                {
                    myIcon = new BMap.Icon("static/image/33.png", new BMap.Size(26,30));
                }
                else if(state == 4)
                {
                    myIcon = new BMap.Icon("static/image/44.png", new BMap.Size(26,30));
                }
                else if(state == 5)
                {
                    myIcon = new BMap.Icon("static/image/55.png", new BMap.Size(26,30));
                }
                else if(state == 6)
                {
                    myIcon = new BMap.Icon("static/image/66.png", new BMap.Size(26,30));
                }
                else
                {
                    alert("Error state type!!");
                }

                var marker = new BMap.Marker(pt, {icon:myIcon}); //创建标注
                map.addOverlay(marker); //讲标注添加到地图中
            }
        }
    });
}


var alarmcnt = null;

function alarmListen() {

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
}



//websocket========================================================================================
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
/*        stompClient.subscribe('/topic/info', function(calResult){
            console.log(calResult);
        });*/

        stompClient.subscribe('/topic/showResult', function(calResult){
            //console.log(calResult);
            var measureInfo = JSON.parse(calResult.body);
            //showResult(measureInfo)

            //修改dom输出到input显示框中
            document.getElementById('idtest').innerText = measureInfo.value;
        });

        stompClient.subscribe('/topic/alarm', function(calResult)
        {
            //取数据库中警报数据显示在徽章中，并且新添加的数据加1
            alarmcnt += 1;
            document.getElementById("alarmid").innerHTML = alarmcnt;
        });
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}