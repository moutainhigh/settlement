/**
 * Created by Administrator on 2019/11/15.
 */
var formSelects = layui.formSelects;
// 监听下拉框选中值
formSelects.on('deptSelect', function(id, vals, val, isAdd, isDisabled){
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


// 检查邮箱是否存在
function checkDicCodeIsExist(dicCode, layer,mode) {
    var flag = true;
    $.ajaxSettings.async = false;
    $.get('/sys-data-dic/isexist/' + dicCode,null, function(r) {
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

// 设置默认密码
function setPass() {
    // 此处应该从后台取得
    $("#password").val("Abcd1234");
}
// 页面渲染
function render(){
    layui.use('form', function(){
        var form = layui.form;
        form.render();
    });
}

function stopOpen (title,url,w,h,full) {
    if (title == null || title == '') {
        var title=false;
    };
    if (url == null || url == '') {
        var url="404.html";
    };
    if (w == null || w == '') {
        var w=($(window).width()*0.9);
    };
    if (h == null || h == '') {
        var h=($(window).height() - 50);
    };
    var index = layer.open({
        type: 2,
        area: [w+'px', h +'px'],
        fix: false, //不固定
        maxmin: true,
        // closeBtn: 0,
        shadeClose: true,
        shade:0.4,
        title: title,
        content: url,
        cancel: function (index, layero) {
            parent.location.reload();
        }
    });
    if(full){
        layer.full(index);
    }
}