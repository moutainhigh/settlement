//////////////////////////////JS////////////////////////////////

function editFun(projectId,data){
    //判断考勤完成时间
    if(!judgeCompleteTime(projectId)) {
        layer.msg('超过考勤结算周期内无法修改',{icon:5});
        return;
    }
    //判断考勤结算时间
    if(!judgeStopTime(projectId)) {
        layer.msg('在结算周期内修改考勤必须提交申请',{icon:5});
        return;
    }
    //判断修改次数

    //判断修改状态
    if(checkSubStatus(data.subStatus,'A')) {
        //layer.msg('已提交修改申请的数据请到【考勤申请修改记录】中修改',{icon:5});
        layer.msg('申请修改的数据请在【工时申请修改记录】中修改',{icon:5});
        return;
    }
    //判断提交状态
    if(checkSubStatus(data.subStatus,'S')) {
        //layer.msg('已提交修改申请的数据请到【考勤申请修改记录】中修改',{icon:5});
        layer.msg('已提交的数据请通过【申请修改】来操作修改',{icon:5});
        return;
    }

    xadmin.open('编辑考勤信息','/ba-work-attendance/edit/'+ data.id,800,450);
}
function checkTimeParam(){

}


//申请修改记录
function applyModify(title,url,projectId,applyCount,totalApplyCount,table,layer,w,h) {
    var checkStatus = table.checkStatus('dataTable')
        ,data = checkStatus.data;
    var ids2=[];
    if (data.length == 0) {
        layer.alert('请先选择要修改的考勤记录',{icon : 5});
        return ;
    }
    for(var i=0;i<data.length;i++) {
        ids2[i]=data[i].id;
    }
    //判断当月修改的次数

    if(applyCount>=totalApplyCount) {
        layer.msg('您已经超过本月修改申请的次数',{icon:5});
        return;
    }
    //修改中的数据不可重复申请修改
    if(getSubStatus(data,'A').length >0) {
        var noSubmitData = getSubStatus(data, 'A');
        layer.msg("申请修改中的数据【" + noSubmitData + "】不可重复修改", {icon: 6});
        return;
    }
    //结算时间之前  //未提交的提示不需要申请修改
    if(judgeStopTime(projectId)) {
        if( getSubStatus(data,'N').length == data.length){
            layer.msg("在结算时间之前,未提交的数据,可以直接编辑",{icon:6});
            return;
        } else if(getSubStatus(data,'S').length < data.length) {
            var noSubmitData=getSubStatus(data,'N');
            layer.msg("未提交过的数据【"+noSubmitData+"】可以直接编辑",{icon:6});
            return;
        } else if(getSubStatus(data,'S').length == data.length) {
            //提交的数据
            layer.msg("在结算时间之前,已经提交的数据可以申请修改",{icon:6});
            //判断当月修改的次数
            xadmin.open(title,url + "/"+projectId+"/"+ ids2,w,h);
            return;
        }
    } else{
        //超过考勤时间不可以提交
        //layer.msg('超过考勤结算时间点,无法提交考勤记录', {icon: 5});
        //return;
    }
    // 修改中的数据不能提交
    if(checkApplyModify(ids2)) {
        layer.msg("申请修改中的数据不要重复提交",{icon:5});
        return;
    }
    //考勤结算周期内
    if(!judgeStopTime(projectId) && judgeCompleteTime(projectId)){
        //未提交的数据不可以修改
        xadmin.open(title+2,url + "/"+projectId+"/"+ ids2,w,h);
        return;
    } else {
        layer.msg('超过考勤结算完成时间点,无法修改考勤记录', {icon: 5});
        return;
    }
    //结算时间之前,已经提交的可以修改

    //进入结算时间的 判断是否在结算时间之内

    //判断当月修改的次数
    // for(var i=0;i<data.length;i++) {
    //     //ids+=data[i].id+",";
    //     ids2[i]=data[i].id;
    // }
    // xadmin.open(title,url + "/"+ ids2,w,h);
}
//提交考勤记录
function commitWorkAttendanceOper(url, projectId,stopTime,compelteTime,table,layer) {
    var checkStatus = table.checkStatus('dataTable')
        ,data = checkStatus.data;
    var ids2=[];

    if (data.length == 0) {
        layer.alert('请先选择要提交的考勤记录',{icon : 5});
        return ;
    }

    //判断是否在考勤结算时间点提交
    if(!judgeCompleteTime(projectId)) {
        //超过考勤时间不可以提交
        layer.msg('超过考勤结算时间点,无法提交考勤记录', {icon: 5});
        return;
    } else if(getSubStatus(data,'S').length > 0) {
        //包含已经提交的数据不可以提交
        var SubmitData=getSubStatus(data,'S');
        layer.msg("提交过的数据【"+SubmitData+"】不要重复提交",{icon:6});
        return;
    }
    for(var i=0;i<data.length;i++) {
        ids2[i]=data[i].id;
        //存在修改的记录，不要重复提交
    }
    // 修改中的数据不能提交
    if (checkApplyModify(ids2)) {
        layer.msg("申请修改中的数据不能提交",{icon:5});
        return;
    }
    layer.open({
        content: '确认提交考勤记录数据？'
        ,btn: ['确定','取消']
        ,yes: function(index){
            // 发送ajax请求  提交考勤记录数据
            $.ajax({
                url:url+ids2,
                type:'put',
                success: function(r){
                    if("commit_200"==r.code){
                        layer.close(index);
                        layer.msg('提交成功', {icon: 6});
                        //执行重载
                        table.reload('dataTable', {
                            page: {
                                curr: 1 //重新从第 1 页开始
                            }
                        }, 'data');
                    } else {
                        layer.msg('提交失败', {icon: 5});
                    }
                }
            });
        }
        ,btn2: function(index){
            //按钮【按钮二】的回调
            layui.table.render();
            layer.close(index);
        }
        ,cancel: function(){
            //右上角关闭回调
            layui.table.render();
        }
    });

}
//生成考勤记录
function generateFun(projectId) {
    if (!judgeStopTime(projectId)) {
        layer.msg('在结算周期内不可以生成考勤记录', {icon: 5});
        return;
    } else {
        xadmin.open('生成考勤数据','/ba-work-attendance/generate/'+projectId,700,500);
    }
}
//导入
function importFun(projectId,upload) {
    if(!judgeStopTime(projectId)) {
        layer.msg('在结算周期内不可以批量导入,请在结算周期前导入',{icon:5});
        return;
    } else {
        $('#import').attr("id",'import2');
        upload.render({
            elem: '#import2' //绑定元素
            ,url: '/file/upload/workattendance' //上传接口
            ,method: 'POST'
            ,accept: 'file'
            ,exts: 'xls|xlsx|csv' //只允许上传excel文件
            ,size: 200*1024 //文件大小
            ,before: function(obj){
            }
            ,done: function(res){//上传完毕回调
                return layer.msg(res.msg,{icon: res.code == '9006' ? 6 : 5});
            }
            ,error: function(){//请求异常回调
                layer.msg('网络异常,文件上传失败,请重新导入！',{icon:5});
            }
        });
    }
}
//检查考勤是否在申请修改中
// 0 存在修改数据
// 1 不存在修改数据
function checkApplyModify(ids) {
    var flag=false;
    var status='A';
    $.ajax({
        type:'get'
        ,url:'/ba-work-attendance/check/status/'+ids
        ,async:false //同步处理
        ,traditional:true
        ,success:function(r){
            if(r.code=='1') {
                flag = false;
            }
            else {
                flag = true;
            }
        }
    });
    return flag;

}
//根据提交的状态S N获得对应的数组列表
function getSubStatus(data,subStatusValue) {
    var idSubStatus=[];
    var j=0;
    for(var i=0;i<data.length;i++) {
        //提交状态
        if(checkSubStatus(data[i].subStatus,subStatusValue)){
            idSubStatus[j++]=data[i].employeeId;
        }
    }
    return idSubStatus;
}
//检查提交的状态S 返回true
function checkSubStatus(val,subStatusValue) {
    if(val == subStatusValue){
        return true;
    } else
        return  false;
}
//检查是否在结算时间之间
function judgeStopTime(projectId){
    var flag=false;
    $.ajax({
        type:'get'
        ,url:'/ba-time-param/judge/workattendance/stoptime/'+projectId
        ,async:false //同步处理
        ,success:function(r){
            if(r.code=='1') {
                flag = false;
            }
            else {
                flag = true;
            }
        }
    });
    return flag;
}
//检查是否在结算时间之间
function judgeCompleteTime(projectId){
    var flag=false;
    $.ajax({
        type:'get'
        ,url:'/ba-time-param/judge/workattendance/completetime/'+projectId
        ,async:false //同步处理
        ,success:function(r){
            if(r.code=='1') {
                flag = false;
            }
            else {
                flag = true;
            }
        }
    });
    return flag;
}
//获得考勤日期
function judgeDate(projectId) {
    $.ajax({
        type:'get'
        ,url:'/ba-time-param/judge/workattendance/date/'+projectId
        ,success:function(r){
            if(r.code=='1')
                return true;
            else
                return false;
        }
    });
}