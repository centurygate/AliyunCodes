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
});


function RunBaiduMap(requestUrl){
    // 百度地图API功能
    var map = new BMap.Map("assetmap");    // 创建Map实例
    var opts = {type: BMAP_NAVIGATION_CONTROL_ZOOM}
    map.addControl(new BMap.NavigationControl(opts));
    map.centerAndZoom(new BMap.Point(118.8106, 32.0480), 14);  // 初始化地图,设置中心点坐标和地图级别
    map.addControl(new BMap.MapTypeControl({mapTypes: [BMAP_NORMAL_MAP,BMAP_SATELLITE_MAP ]}));
    map.setCurrentCity("南京");          // 设置地图显示的城市 此项是必须设置的
    map.enableScrollWheelZoom(true);     //开启鼠标滚轮缩放

    //创建绿点设施示意
    if(document.createElement('canvas').getContext){ 
        
        var points = [];

        //Ajax获取服务器端坐标点
        $.ajax({
            url: requestUrl,
            type: "GET",
            success: function (data) {
                ajaxobj=eval("("+data+")");

                for(var i=0;i<ajaxobj.data.length;i++)
                {
                    var posx = ajaxobj.data[i].positionx;
                    var posy = ajaxobj.data[i].positiony;
                    points.push(new BMap.Point(posx, posy));
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
    } else{
        alert("浏览器版本不支持！！");
    }
}