/**
 * Created by Administrator on 2016/12/5.
 */

var sheetdataURL = project_url + "/serversheetdata";
$(function () {
    $.ajax({
        url: sheetdataURL,
        type: "GET",
        success: function (data) {
            ajaxobj = eval("(" + data + ")");
            var ctable = document.getElementById("sheettable");

            for (var i = 0; i < ajaxobj.data.length; i++) {
                var newRow = ctable.insertRow(i + 1);
                var col1 = newRow.insertCell(0);
                var col2 = newRow.insertCell(1);
                var col3 = newRow.insertCell(2);
                var col4 = newRow.insertCell(3);
                var col5 = newRow.insertCell(4);
                var col6 = newRow.insertCell(5);
                col1.innerHTML = ajaxobj.data[i].item;
                col2.innerHTML = ajaxobj.data[i].department;
                col3.innerHTML = ajaxobj.data[i].facility;
                col4.innerHTML = ajaxobj.data[i].time;
                col5.innerHTML = ajaxobj.data[i].state;

                $(col6).html("<input class='btn btn-info btn-sm btn-edit' id='" + i + "' type='button' value='编辑' onclick='doedit(this)'/>" + "&nbsp;" +
                    "<input class='btn btn-danger btn-sm btn-del' id='" + i + "' type='button' value='删除' onclick='dodelete(this)'/>");
            }
        }
    });
});

function dodelete(obj) {
    var tourl = document.getElementById(obj.id).parentNode.parentNode.cells(0).innerHTML;
    window.location.href = "dosheetdelete.action?name=" + tourl;
}

function doedit(obj) {

    //打开模态框遮罩
    $('#myModal2').modal('show');

    //原有数值填入表单
    var row = document.getElementById(obj.id).parentNode.parentNode;
    document.getElementById('idname').value = row.cells(0).innerHTML;
    document.getElementById('iddepartment').value = row.cells(1).innerHTML;
    document.getElementById('idasset').value = row.cells(2).innerHTML;
    document.getElementById('idtime').value = row.cells(3).innerHTML;
    document.getElementById('idstate').value = row.cells(4).innerHTML;
}