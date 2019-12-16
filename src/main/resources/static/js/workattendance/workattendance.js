

/** 修改考勤记录操作 */
function editWorkAttendanceOper(title, url, table, layer,w,h) {
    var checkStatus = table.checkStatus('dataTable')
        ,data = checkStatus.data;
    if (data.length == 0) {
        layer.alert('请先选择一行',{icon : 5});
    }else if (data.length > 1) {
        layer.alert('不能多选啊，只能选择一行',{icon : 5});
    }else if(data.length == 1) {
        // 项目组只有处于审核通过状态才能操作
        // if (data[0].checkStatus == passCheckStatus) {
        xadmin.open(title,url + data[0].id,w,h);
        // } else {
        //     layer.msg("项目组只有处于【审核通过】状态才能" + title,{icon:5});
        // }
    }
}