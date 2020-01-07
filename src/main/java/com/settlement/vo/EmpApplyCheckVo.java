package com.settlement.vo;

import com.settlement.entity.BaEmpApplyCheck;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 员工申请审核Vo
 *
 * @auth admin
 * @date 2019-12-23
 */
@Data
public class EmpApplyCheckVo extends BaEmpApplyCheck implements Serializable {
    /** 申请人姓名 */
    private String applyUserContent;
    /** 审核人姓名 */
    private String checkUserContent;
    /** 审核状态 */
    private String checkStatusContent;
    /** 申请项目组 */
    private String projectName;
    /** 审核结果 */
    private String checkResult;

}
