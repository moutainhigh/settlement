function checkResultVerify(data, checkResultPass,checkResultNoPass,layer) {
    var ret = new Object();
    ret.flag = true;
    // 审核通过：必须选择客户
    if (data.checkResult == checkResultPass) {
        if (data.customerId == null || data.customerId == '') {
            ret.flag = false;
            ret.message = '审核通过，客户必须选择一个啊';
        }
        // 审核未通过： 必须填写备注
    } else if (data.checkResult == checkResultNoPass) {
        if (data.remark == null || data.remark == '') {
            ret.flag = false;
            ret.message = '审核未通过，备注必须填写啊';
        }
    }
    if (!ret.flag) {
        layer.msg(ret.message, {icon : 5});
    }
    return ret;
}