package com.settlement.co;

import lombok.Data;

import java.sql.Date;

@Data
public class ApplyCo extends PageCo{
    private Integer id;
    private Integer applyUser;
    private Integer checkUser;
    private String roleCode;
    private String keyword;
    private String checkStatus;
    private String applyDate;
    private String updateStatus;
    private String mode;
}
