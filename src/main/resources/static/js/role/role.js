
// 检查邮箱是否存在
function checkRoleCodeIsExist(roleCode, layer,mode) {
    var flag = true;
    $.ajaxSettings.async = false;
    $.get('/sys-role/isexist/' + roleCode,null, function(r) {
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