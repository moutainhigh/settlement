package com.settlement.utils;

/**
 * @description 常量类
 *
 * @author  admin
 * @date 2019/11/11.
 */
public class Const {
    /** 功能类型M */
    public final static String MENU_TYPE_M = "M";
    /** 启用状态Y-可用 */
    public final static String ENABLED_Y = "Y";
    /** 启用状态N-不可用*/
    public final static String ENABLED_N = "N";
    /** 删除状态N-使用中 */
    public final static String DEL_FLAG_N = "N";
    /** 删除状态D-删除 */
    public final static String DEL_FLAG_D = "D";
    /** 部门根节点CODE */
    public final static String DEPT_ROOT = "ROOT";
    /** 部门根节点ID */
    public final static Integer DEPT_ROOT_ID = 1;
    /** 数据字典根节点ID */
    public final static Integer DATA_DIC_ROOT = 0;
    /** 权限菜单根节点ID */
    public final static Integer PERMISSION_ROOT_ID = 0;
    /** 添加标志 */
    public final static String MODE_ADD = "add";
    /** 修改标志 */
    public final static String MODE_UPDADTE = "update";
    /** slat */
    public final static String SALT = "12345678";
    /** 客户经理角色CODE:AM */
    public final static String ROLE_CODE_AM = "AM";
    /** 助理角色CODE：ASSISTANT */
    public final static String ROLE_CODE_ASSISTANT = "ASSISTANT";
    /** 结算负责人角色CODE：SETTLEMENT */
    public final static String ROLE_CODE_SETTLEMENT = "SETTLEMENT";
    /** 审核状态NO_CHECK：待审核 */
    public final static String CHECK_STATUS_NO_CHECK = "NO_CHECK";
    /** 审核状态CHECK_PASS：审核通过*/
    public final static String CHECK_STATUS_CHECK_PASS = "CHECK_PASS";
    /** 审核状态CHECK_NOTPASS：审核未通过*/
    public final static String CHECK_STATUS_CHECK_NOPASS = "CHECK_NOPASS";
    /** 员工提交状态S：已提交 */
    public final static String EMP_SUBMIT_STATUS_S = "S";
    /** 员工提交状态N：未提交*/
    public final static String EMP_SUBMIT_STATUS_N = "N";
    /** 级别填写模式CODE： LEVEL_INPUT_TYPE*/
    public final static String LEVEL_TYPE_PARENT_CODE = "LEVEL_INPUT_TYPE";
}
