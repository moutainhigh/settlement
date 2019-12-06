/** 级别 手动填写 */
function handInput(mode) {
    // mode = [[ ${LEVEL_MODE_H} ]];
    $("#div1").hide();
    $("#posLevelSelect").removeAttr("name");
    $("#posLevelSelect").val('');
    $("#div2").show();
    $("#posLevelInput").val('');
    $("#posLevelInput").attr("name","posLevel");
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
    $("#posLevelSelect").attr("name","posLevel");
    $("#posLevelSelect").val('');
    $("#div2").hide();
    $("#posLevelInput").removeAttr("name");
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
    $.get('/ba-project-employee/isexist/' + code,null,function (r) {
        if (r.code == 1)  {
            flag = false;
            layer.alert(r.msg, {icon: 5});
        } else if (mode != 'submit' && r.code == 0) {
            layer.alert(r.msg, {icon: 6});
        }
    });
    return flag;
}