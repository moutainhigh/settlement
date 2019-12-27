function checkResultVerify(data, checkResultPass,checkResultNoPass,layer) {
    var ret = new Object();
    ret.flag = true;
    // 审核通过：必须输入口令
    if (data.checkResult == checkResultPass) {
        if (data.updatePassword == null || data.updatePassword == '') {
            ret.flag = false;
            ret.message = '审核通过，口令必须填写啊';
        }
        // 审核未通过： 必须填写备注
    } else if (data.checkResult == checkResultNoPass) {
        if (data.checkRemark == null || data.checkRemark == '') {
            ret.flag = false;
            ret.message = '审核未通过，备注必须填写啊';
        }
    }
    if (!ret.flag) {
        layer.msg(ret.message, {icon : 5});
    }
    return ret;
}