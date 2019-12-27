/** 级别 手动填写 */
function handInput(mode) {
    // mode = [[ ${LEVEL_MODE_H} ]];
    $("#div1").hide();
   // $("#posLevelSelect").removeAttr("name");
    $("#posLevelSelect").val('');
    $("#div2").show();
    $("#posLevelInput").val('');
    //$("#posLevelInput").attr("name","posLevel");
    $("#priceMonth").val('');
    $("#priceMonth").removeAttr("readonly");
    $("#handInputId").hide();
    $("#frameInputId").show();
    $("#levelMode").val(mode);
     layui.form.render();
}

/** 级别 框架选择 */
function frameInput(mode) {
    $("#div1").show();
    //$("#posLevelSelect").attr("name","posLevel");
    $("#posLevelSelect").val('');
    $("#div2").hide();
   // $("#posLevelInput").removeAttr("name");
    $("#priceMonth").val('');
    $("#priceMonth").attr("readonly","readonly");
    $("#handInputId").show();
    $("#frameInputId").hide();
    $("#levelMode").val(mode);
    layui.form.render();
}

/** 检查员工编号是否重复 */
function checkEmpCodeIsExist(code,layer,mode) {
    var flag = true;
    $.ajaxSettings.async = false;
    $.get('/ba-employee/isexist/' + code,null,function (r) {
        if (r.code == 1)  {
            flag = false;
            layer.alert(r.msg, {icon: 5});
        } else if (mode != 'submit' && r.code == 0) {
            layer.alert(r.msg, {icon: 6});
        }
    });
    return flag;
}

/** 员工-删除*/
function emp_del(id){
    $.ajax({
        url:"/ba-employee/del/" + id,
        type:"DELETE",
        contentType:"application/json",//设置请求参数类型为json字符串
        // data: {id:id},
        dataType:"json",
        success:function(r){
            layer.msg(r.msg,{icon: r.code == 'delete_200' ? 6: 5});
        }
    });
    timeout(2000);
}

/** 员工提交 */
function submitEmp(layer,data,pgId) {
    if (data.length == 0) {
        layer.alert('请先选择一行',{icon : 5});
    } else {
        layer.confirm('确认提交么?提交后员工信息将参与考勤、结算，编辑需要申请', function(index){
            var ids = '';
            for (var i = 0; i < data.length; i++) {
                if (i == data.length - 1) {
                    ids = ids + data[i].id
                } else {
                    ids = ids + data[i].id + ',';
                }
            }
            $.ajax({
                url: '/ba-pg-employee/sub',
                type: 'POST',
                async: false,
                data: {ids: ids,pgId: pgId},
                success: function(r) {
                    layer.msg(r.msg,{icon: r.code == '200' ? 6: 5});
                }
            });
            layer.close(index);
            timeout(2000);
        });
    }
}

/** 员工申请修改 */
function apply(data, layer, url) {
    if (data.length === 0) {
        layer.alert("请先选择一行",{icon:5});
    } else {
        xadmin.open('员工申请修改',url,800,420);
    }
}



