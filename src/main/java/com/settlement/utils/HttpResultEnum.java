package com.settlement.utils;

/**
 * @description 服务器返回枚举
 *
 * @author admin
 * @date 2019/11/08.
 */
public enum HttpResultEnum {

    CODE_200("200", "成功"),
    CODE_500("500","失败"),
    CODE_0("0","success"),
    CODE_1("1", "fail"),
    // USER
    USER_ISEXIST_0("0","邮箱不存在，可以添加"),
    USER_ISEXIST_1("1","邮箱已经存在，不可以添加"),
    // 项目组
    PG_CODE_0("0","项目组不存在，可以添加"),
    PG_CODE_1("1","项目组已经存在，不可以添加"),
    PG_ASSISTANT_0("0","项目组未关联助理"),
    PG_SETTLEMENT_0("0","项目组未关联结算负责人"),
    //角色编码
    ROLE_CODE_0("0","角色编码不存在，可以添加"),
    ROLE_CODE_1("1","角色编码已经存在，不可以添加"),
    // 数据字典编码
    DIC_CODE_0("0","数据字典编码不存在，可以添加"),
    DIC_CODE_1("1","数据字典编码已经存在，不可以添加"),
    //菜单编码
    PERMISSION_CODE_0("0","菜单编码不存在，可以添加"),
    PERMISSION_CODE_1("1","菜单编码已经存在，不可以添加"),
    //部门编码
    DEPT_CODE_0("0","部门编码不存在，可以添加"),
    DEPT_CODE_1("1","部门编码已经存在，不可以添加"),
    // 时间点编码
    TIME_PARAM_CODE_0("0","时间点参数编号不存在，可以添加"),
    TIME_PARAM_CODE_1("1","时间点参数编号经存在，不可以添加"),
    //考勤时间点
    STOP_TIME_CODE_0("0","未进入结算时间，可以提交和修改"),
    STOP_TIME_CODE_1("1","已进入结算时间，不可以提交和修改"),
    //考勤完成时间点
    COMPLETE_TIME_CODE_0("0","未进入考勤完成时间，可以提交和修改"),
    COMPLETE_TIME_CODE_1("1","已超过考勤完成时间，不可以提交和修改"),
    // 公式参数编码
    FORMULA_PARAM_CODE_0("0","公式参数编号不存在，可以添加"),
    FORMULA_PARAM_CODE_1("1","公式参数编号已经存在，不可以添加"),

    // 导出参数编码
    EXPORT_COL_VALUE_0("0","导出参数字段值不存在，可以添加"),
    EXPORT_COL_VALUE_1("1","导出参数字段值已经存在，不可以添加"),

    //合同编号
    CONTRACT_NO_0("0","合同编号不存在，可以添加"),
    CONTRACT_NO_1("0","合同编号已经存在，不可以添加"),

    LOGIN_CODE_500("login_500","登录失败,用户名或密码错误"),
    LOGIN_CODE_501("login_501","用户名不正确"),
    LOGIN_CODE_502("login_502","密码不正确"),
    PARAM_CODE_500("valid_500","缺少必填参数"),
    DEL_CODE_200("delete_200","删除成功"),
    DEL_FAIL_CODE_200("delete_fail_200","删除失败"),
    DEL_CODE_500("delete_500","删除异常"),
    ADD_CODE_200("add_200","新增成功"),
    ADD_CODE_500("add_500","新增失败"),
    EDIT_CODE_200("edit_200","修改成功"),
    EDIT_CODE_500("edit_500","修改失败"),
    QUERY_CODE_200("query_200","查询成功"),
    QUERY_CODE_500("query_500","查询失败"),
    //考勤记录提交状态
    COMMIT_CODE_200("commit_200","提交成功"),
    COMMIT_CODE_500("commit_500","提交失败"),

    //口令验证
    VERIFY_CODE_500("verify_500","口令验证失败"),
    VERIFY_CODE_200("verify_200","口令验证通过"),

    EMP_CODE_0("0","员工不存在，可以添加"),
    EMP_CODE_1("1","员工已经存在，不能添加"),

    FILE_UPLOAD_CODE_9006("9006","文件上传成功"),
    FILE_UPLOAD_CODE_9000("9000","文件上传失败"),
    FILE_UPLOAD_CODE_9003("9003","文件上传超过限制！"),
    FILE_UPLOAD_CODE_9001("9001","创建文件夹失败"),
    FILE_UPLOAD_CODE_9002("9002","文件下载失败"),
    FILE_UPLOAD_CODE_9004("9004","图片上传格式不正确，支持的格式有jpg、png、jpeg"),
    FILE_UPLOAD_CODE_9005("9005","图片上传为空");

    private String code;
    private String message;

    // 构造方法
    HttpResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取名称
     * @return 名称
     */
    public String getCode() {

        return this.code;
    }

    /**
     * 获取索引
     * @return 索引
     */
    public String getMessage() {
        return this.message;
    }

}
