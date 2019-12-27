package com.settlement.co;

import lombok.Data;


/**
 * <p>
 *    申请员工Co
 * </p>
 *
 * @auth admin
 * @date 2019-12-26
 */
@Data
public class EmpApplyCo extends PageCo {

    private Integer applyId;

    private String levelModeF;

    private String levelModeH;

}
