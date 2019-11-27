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
    //角色编码
    ROLE_CODE_0("0","角色编码不存在，可以添加"),
    ROLE_CODE_1("1","角色编码已经存在，不可以添加"),
    // 数据字典编码
    DIC_CODE_0("0","数据字典编码不存在，可以添加"),
    DIC_CODE_1("1","数据字典编码已经存在，不可以添加"),

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

    FILE_UPLOAD_CODE_9000("9000","文件上传失败"),
    FILE_UPLOAD_CODE_9003("9003","文件上传超过限制！"),
    FILE_UPLOAD_CODE_9001("9001","创建文件夹失败"),
    FILE_UPLOAD_CODE_9002("9002","文件下载失败");


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
