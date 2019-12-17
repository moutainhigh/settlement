/**
 * Created by Administrator on 2019/11/15.
 */
var formSelects = layui.formSelects;
// 监听下拉框选中值
formSelects.on('projectSelect', function(id, vals, val, isAdd, isDisabled){
    //id:           点击select的id
    //vals:         当前select已选中的值
    //val:          当前select点击的值
    //isAdd:        当前操作选中or取消
    //isDisabled:   当前选项是否是disabled
    //如果return false, 那么将取消本次操作
    // return false;
    var deptId = formSelects.value("deptSelect",'val');
    if (deptId != null && deptId != '') {
        // 取得部门对应的角色
        $.get('/sys-role/dept/'+deptId,null, function(r) {
            $("#roleDiv").empty();
            if (r.code == 200) {
                for (var i = 0; i < r.data.length; i++) {
                    var role = r.data[i];
                    var inputRole = $("<input type='radio' name='role.id' value='" + role.id + "' title='" + role.roleCnName + "'>");
                    $("#roleDiv").append(inputRole);
                }
                render();
            }
        });
    }
},true);