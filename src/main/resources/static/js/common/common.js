/** 延时刷新 */
function timeout(time) {
    setTimeout(function () {
        // location.reload();
        // var index = parent.layer.getFrameIndex(window.name);
        layui.table.reload('dataTable');//重载父页表格，参数为表格ID
     //    parent.layer.close(index);
    }, time);
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
