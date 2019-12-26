package com.settlement.co;

import lombok.Data;

import java.util.Date;

/**
 * <p>
 *     项目组员工Co
 * </p>
 */
@Data
public class EmployeeCo extends PageCo{

    private String keyword;

    private Integer pgId;

   // private String submitStatus;

    private String delFlag;

    private String entranceStatus;

    private Date currentTime;

}
