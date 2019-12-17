/** 延时刷新 */
function timeout(time) {
    setTimeout(function () {
        // location.reload();
        // var index = parent.layer.getFrameIndex(window.name);
        layui.table.reload('dataTable');//重载父页表格，参数为表格ID
     //    parent.layer.close(index);
    }, time);
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

/** 确定 */
function defined(data, layer, url) {
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