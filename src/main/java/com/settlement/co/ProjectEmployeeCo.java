package com.settlement.co;

import lombok.Data;

/**
 * <p>
 *     项目组员工Co
 * </p>
 */
@Data
public class ProjectEmployeeCo extends PageCo{

    private String keyword;

    private Integer pgId;

    private String submitStatus;

    private String delFlag;

}
