package com.settlement.co;

import lombok.Data;

/**
 * <p>
 *     员工申请修改Co
 * </p>
 *
 * @auth admin
 * @date 2019-12-24
 */
@Data
public class EmpApplyCheckCo extends PageCo {
    /** 审核状态 */
    private String checkStatus;
    /**  申请日期 */
    private String applyDate;
    /** 审核人 */
    private Integer checkUserId;
    /**  申请人 */
    private Integer applyUserId;

}
