package com.settlement.utils;

/**
 * @description Http返回状态枚举
 *
 * @author admin
 * @date 2019/11/08.
 */
public enum HttpStateEnum {

    OK("服务器成功返回", "200"),
    INVALID_REQUEST("参数请求错误", "400"),
    UNAUTHORIZED("用户名密码错误", "401"),
    FORBIDDEN("访问被禁止", "403"),
    NOT_FOUND("服务器无法正常提供信息", "404"),
    INTERNAL_SERVER_ERROR("亲，请求失败呦！", "500"),
    TIME_OUT("网络请求超时", "504"),
    PARAM_ERROR("必填参数不能为空", "PARAM_400"),
    PARAM_NULL("请填写参数", "PARAM_401"),
    SELECT_ERROR("查询失败", "SELECT_501"),
    SELECT_NULL("未找到您所查询的信息", "SELECT_502"),
    SELECT_EXCEP("查询异常", "SELECT_500"),
    INSERET_ERROR("创建失败", "INSERET_501"),
    INSERET_EXCEP("创建异常", "INSERET_500"),
    UPDATE_ERROR("更新失败", "UPDATE_501"),
    UPDATE_EXCEP("更新异常", "UPDATE_500"),
    DELETE_ERROR("删除失败", "DELETE_501"),
    DELETE_EXCEP("删除异常", "DELETE_500");

    private String name;
    private String index;

    HttpStateEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    /**
     * 获取名称
     * @return 名称
     */
    public String getName() {

        return this.name;
    }

    /**
     * 获取索引
     * @return 索引
     */
    public String getIndex() {
        return this.index;
    }
}
