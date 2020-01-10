/** 延时刷新 */
function timeout(time) {
    setTimeout(function () {
        // location.reload();
        // var index = parent.layer.getFrameIndex(window.name);
        layui.table.reload('dataTable');//重载父页表格，参数为表格ID
     //    parent.layer.close(index);
    }, time);
}

function timeoutClose(time,dataName) {
    setTimeout(function () {
        var index = parent.layer.getFrameIndex(window.name);
        parent.layer.close(index);
        layui.table.reload(dataName);//重载父页表格，参数为表格ID
    }, time);
}
/**停留刷新在当前页面**/
function reloadTable(tableName){
    // 刷新数据，保留在当前页
    var index = parent.layer.getFrameIndex(window.name);
    parent.layui.table.reload(tableName);//重载父页表格，参数为表格ID
    parent.layer.close(index);
}

// 检查编码是否存在
function checkCodeIsExist(url,code, layer,mode) {
    var flag = true;
    $.ajaxSettings.async = false;
    $.get(url + code,null, function(r) {
        if (r.code == 1)  {
            flag = false;
            layer.alert(r.msg, {icon: 5});
        } else if (mode != 'submit' && r.code == 0) {
            layer.alert(r.msg, {icon: 6});
        }
    });
    $.ajaxSettings.async = true;
    return flag;
}

/** 保存 */
function save(data, layer,url) {
    $.ajax({
        url: url,
        type:"POST",
        // contentType:"application/json",//设置请求参数类型为json字符串
        data: data.field,
        // dataType:"json",
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $(".layui-btn").attr({ disabled: "disabled" });

        },
        success:function(r){
            layer.alert(r.msg, {
                    icon : r.code == '200' ? 6: 5
                },
                function() {
                    // 刷新数据，保留在当前页
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload('dataTable');//重载父页表格，参数为表格ID
                    parent.layer.close(index);
                });
            $(".layui-btn").removeAttr('disabled');
        }
    });
}

/** 提交 */
function submit(data, layer,url) {
    $.ajax({
        url: url,
        type:"PUT",
        // contentType:"application/json",//设置请求参数类型为json字符串
        data: data.field,
        // dataType:"json",
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $(".layui-btn").attr({ disabled: "disabled" });

        },
        success:function(r){
            layer.alert(r.msg, {
                    icon : r.code == '200' ? 6: 5
                },
                function() {
                    // 刷新数据，保留在当前页
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload('dataTable');//重载父页表格，参数为表格ID
                    parent.layer.close(index);
                });
            $(".layui-btn").removeAttr('disabled');
        }
    });
}

/** 修改 */
function edit(data, layer, url) {
    // 处理重复提交
    $.ajax({
        url: url,
        type:"PUT",
        // contentType:"application/json",//设置请求参数类型为json字符串
        data: data.field,
        // dataType:"json",
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $(".layui-btn").attr({ disabled: "disabled" });

        },
        success:function(r){
            layer.alert(r.msg, {
                    icon : r.code == 'edit_200' ? 6: 5
                },
                function() {
                    // 刷新数据，保留在当前页
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload('dataTable');//重载父页表格，参数为表格ID
                    parent.layer.close(index);
                });
            $(".layui-btn").removeAttr('disabled');
        }
    });
}

/**
 * 列表页根据条件禁用
 * @param res
 * @param status
 * @param value
 */
function checkboxDisable(res,status,value) {
    res.data.forEach(function (item, index) {
        if (eval(status) == value) {
            var tr = $("tr[data-index='" + index + "']");
            tr.attr({"style":"color:red;font-weight:bold"});
            var td = $(tr.find("td[data-field='id']"));
            var checkbox = $(td.find("input[type='checkbox']"));
            checkbox.attr("disabled",true);
            checkbox.remove();
        }
    });
}
/** 启用 */
function start(url) {
    $.ajax({
        url: url,
        type:"PUT",
        // contentType:"application/json",//设置请求参数类型为json字符串
        // data: data.field,
        // dataType:"json",
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $(".layui-btn").attr({ disabled: "disabled" });

        },
        success:function(r){
            layer.alert(r.msg, {
                    icon : r.code == '200' ? 6: 5
                },
                function() {
                    // 刷新数据，保留在当前页
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload('dataTable');//重载父页表格，参数为表格ID
                    parent.layer.close(index);
                });
            $(".layui-btn").removeAttr('disabled');
        }
    });
}

/** 停用 */
function stop(url) {
    $.ajax({
        url: url,
        type:"PUT",
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $(".layui-btn").attr({ disabled: "disabled" });

        },
        success:function(r){
            layer.alert(r.msg, {
                    icon : r.code == '200' ? 6: 5
                }
                /*function() {
                    // 刷新数据，保留在当前页
                   // var index = parent.layer.getFrameIndex(window.name);
                    // layui.table.reload('dataTable');//重载父页表格，参数为表格ID
                    // parent.layer.close(index);
                }*/);
            $(".layui-btn").removeAttr('disabled');
        }
    });
}

/** 审核提交 */
function check(data, layer, url,dataTable) {
    $.ajax({
        url: url,
        type:"PUT",
        data: data.field,
        // dataType:"json",
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $(".layui-btn").attr({ disabled: "disabled" });

        },
        success:function(r){
            layer.alert(r.msg, {
                    icon : r.code == 'chk_200' ? 6: 5
                },
                function() {
                    // 刷新数据，保留在当前页
                    var index = parent.layer.getFrameIndex(window.name);
                    parent.layui.table.reload(dataTable);//重载父页表格，参数为表格ID
                    parent.layer.close(index);
                });
            $(".layui-btn").removeAttr('disabled');
        }
    });
}

/** 批量选择，拼接id */
function createIds(obj) {
    var ids = '';
    for (var i = 0; i < obj.length; i++) {
        if (i == obj.length - 1) {
            ids = ids + obj[i].id;
        } else {
            ids = ids + obj[i].id + ',';
        }
    }
    return ids;
}


/** 图片预览 */
function operImg(data) {
    var imgName = data.rateEmailFilename;
    var src = '/img/' + imgName.replace("_","/");
    var a = "<a href='javascript:(0)' class='layui-table-link' onclick=previewImg('"  + data.id + "')>" + imgName + "</a>";
    return a;
}
function previewImg(id) {
    xadmin.open('图片预览','/ba-employee/view/'+ id, 500,500);
}
