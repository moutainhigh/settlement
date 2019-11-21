var formSelects = layui.formSelects;
/** 根据CODE检查项目组是否存在 */
function checkPgIsExist(code, layer,mode) {
    var flag = true;
    $.ajaxSettings.async = false;
    $.get('/ba-project-group/isexist/' + code,null, function(r) {
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