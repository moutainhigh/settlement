package com.settlement.co;

import lombok.Data;


/**
 * <p>
 *    申请修改员工Co
 * </p>
 *
 * @auth admin
 * @date 2019-12-26
 */
@Data
public class EmpApplyCo extends PageCo {
    /** 申请修改ID */
    private Integer applyId;
    /** 手动填写 */
    private String levelModeF;
    /** 框架协议 */
    private String levelModeH;
    /** 修改状态 */
    private String updateStatus;

}
