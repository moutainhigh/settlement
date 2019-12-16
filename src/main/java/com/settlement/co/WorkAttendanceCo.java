package com.settlement.co;

import lombok.Data;

@Data
public class WorkAttendanceCo extends PageCo{
    /**查询条件-查询关键字**/
    private String keyword;
    /**查询条件-审核人id**/
    private Integer checkUserId;
    /**查询条件-项目id**/
    private Integer projectId;
    /**查询条件-当前年**/
    private Integer yearValue;
    /**查询条件-当前月份**/
    private Integer monthValue;
    /**查询条件-员工是否删除标志**/
    private String delFlag;
    /**查询条件-当前项目是否审核 N S**/
    private String subStatus;
    /**查询条件-申请人**/
    private String applyId;
    /**查询条件-入职离场 L**/
    private String entranceStatusL;
    /**查询条件-在场 I**/
    private String entranceStatusI;
    /**查询条件-员工否离职 O L**/
    private String jobStatus;
    /**查询条件-当前月份**/
    private String currentYearMonth;
    /**查询条件-当前月份**/
    private String  leaveTime;


}
