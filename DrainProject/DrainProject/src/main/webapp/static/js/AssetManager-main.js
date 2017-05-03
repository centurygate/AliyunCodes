/**
 * Created by Administrator on 2016/10/28.
 */


$(document).ready(function () {
    var baiduURL = project_url + "/asset";
    try {
        RunBaiduMap(baiduURL);
    }
    catch (exception) {
        console.error("Exception messages lists below----------------->:");
        console.error("exception.type: " + exception.name);
        console.error("exception.message: " + exception.message);
    }

    //点击设施查询-->“水泵”的响应
    $('#shuibeng').click(function () {

        document.getElementById('assetlist').style.display = 'block';
        //清空原有的显示tab项
        $('.searchlist #item-list .item-asset').remove();

        showAssetTypes(baiduURL, 1);

        //动态插入设备tab项
        $.ajax({
            url: baiduURL,
            type: "GET",
            success: function (data) {
                ajaxobj = eval("(" + data + ")");

                //实现html设备tab项的动态插入
                for (var i = 0; i < ajaxobj.length; i++) {

                    if(ajaxobj[i].type == 1){

                        var txt1="<li class='item-asset'>" +
                            "<div class='tab'>"+
                            "<span><a class='no-1'></a></span>水泵设施"+
                            "<div class='shuibeng-image'>"+
                            "<img src='" + project_url + "/static/image/shuibeng.png'/>"+
                            "</div>"+
                            "</div>"+
                            "<hr class='hr-style'/>"+
                            "</li>";
                        $('#item-list li').last().after(txt1);
                    }
                }
            }
        });
        
    });


    //点击设施查询-->“液位计”的响应
    $('#yeweiji').click(function () {

        document.getElementById('assetlist').style.display = 'block';
        //清空原有的显示tab项
        $('.searchlist #item-list .item-asset').remove();

        showAssetTypes(baiduURL, 2);

        //动态插入设备tab项
        $.ajax({
            url: baiduURL,
            type: "GET",
            success: function (data) {
                ajaxobj = eval("(" + data + ")");

                //实现html设备tab项的动态插入
                for (var i = 0; i < ajaxobj.length; i++) {

                    if(ajaxobj[i].type == 2){

                        var txt1="<li class='item-asset'>" +
                            "<div class='tab'>"+
                            "<span><a class='no-1'></a></span>液位计"+
                            "<div class='yeweiji-image'>"+
                            "<img src='" + project_url + "/static/image/yeweiji.png'/>"+
                            "</div>"+
                            "</div>"+
                            "<hr class='hr-style'/>"+
                            "</li>";
                        $('#item-list li').last().after(txt1);
                    }
                }
            }
        });

    });

    
    //点击‘叉号’角标清空设备列表
    $('#close1').click(function(){
        $('#id1').empty();

        //隐藏列表
        var dom = document.getElementsByClassName('searchlist');
        dom[0].style.display = 'none';
        //清空原有的显示tab项
        $('.searchlist #item-list .item-asset').remove();

        //重新显示所有的设备点标识
        showAssetTypes(baiduURL, 0);
    });
    
    
    //点击“安装数量”的设施查询相应
    $("#asset-count").click(function () {

        //调用‘遮罩’窗口
        $('#myModal').modal('show');

        //动态插入图表
        startQueryChart(baiduURL, 'figure-chart');
    })
});


//在百度地图上显示设备绿点，当参数类型为0--所有设备点；1--类型1设备点；2--类型2设备点。
function showAssetTypes(requestUrl, type) {

    //首先清空原有的百度覆盖点
    if(map != null){
        map.clearOverlays();
    }
    
    //数组收集坐标点容器
    var points = [];

    //Ajax获取服务器端坐标点
    $.ajax({
        url: requestUrl,
        type: "GET",
        success: function (data) {
            ajaxobj=eval("("+data+")");

            for(var i=0;i<ajaxobj.length;i++)
            {
                if(type == 0){
                    var posx = ajaxobj[i].positionx;
                    var posy = ajaxobj[i].positiony;
                    points.push(new BMap.Point(posx, posy));
                    continue;
                } 
                else if(type==1 && ajaxobj[i].type==1){
                    var posx = ajaxobj[i].positionx;
                    var posy = ajaxobj[i].positiony;
                    points.push(new BMap.Point(posx, posy));
                    continue;
                }
                else if(type==2 && ajaxobj[i].type==2){
                    var posx = ajaxobj[i].positionx;
                    var posy = ajaxobj[i].positiony;
                    points.push(new BMap.Point(posx, posy));
                    continue;
                }
                else{
                    //do nothing
                }
            }

            var options = {
                size: BMAP_POINT_SIZE_NORMAL,
                shape: BMAP_POINT_SHAPE_CIRCLE,
                color: '#008B00'
            }

            var pointCollection = new BMap.PointCollection(points, options);
            map.addOverlay(pointCollection);
        }
    });
}


var map=null;
function RunBaiduMap(requestUrl){
    // 百度地图API功能
    map = new BMap.Map("assetmap");    // 创建Map实例
    var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM}
    map.addControl(new BMap.NavigationControl(opts));
    map.centerAndZoom(new BMap.Point(118.8106, 32.0480), 14);  // 初始化地图,设置中心点坐标和地图级别
    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP ]}));
    map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    //创建绿点设施示意
    if(document.createElement('canvas').getContext){ 
        
        showAssetTypes(requestUrl, 0);
    } else{
        alert("浏览器版本不支持！！");
    }
}


//模态框设施统计Echart图表
function startQueryChart(requestUrl ,id){

    var myChart;
    //初始化图表控件
    var findid = document.getElementById(id);
    if(findid == null)
    {
        return null;
    }
    else{
        myChart = echarts.init(findid);
    }

    //获取设施的具体数量
    var cnt_shuibeng = null;
    var cnt_yeweiji = null;
    $.ajax({
        url: requestUrl,
        async: false,
        type: "GET",
        success: function (data) {
            ajaxobj=eval("("+data+")");

            for(var i=0;i<ajaxobj.length;i++)
            {
                if(ajaxobj[i].type == 1){
                    cnt_shuibeng++;
                    continue;
                }
                else if(ajaxobj[i].type == 2){
                    cnt_yeweiji++;
                    continue;
                }
                else{
                    //do nothing
                }
            }
        }
    });


    //初始空白没有数据的option
    option = {
        color: ['#3398DB'],
        tooltip : {
            trigger: 'axis',
            axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top: 30,
            containLabel: true
        },
        xAxis : [
            {
                type : 'category',
                data : ['水泵', '液位计'],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis : [
            {
                type : 'value'
            }
        ],
        series : [
            {
                name:'设施数量',
                type:'bar',
                barWidth: '30%',
                data:[cnt_shuibeng, cnt_yeweiji]
            }
        ]
    };
    
    myChart.setOption(option);
}