package com.settlement.co;

import lombok.Data;

import java.sql.Date;

@Data
public class ApplyCo extends PageCo{
    private String keyword;
    private Integer status;
    private Date applyDate;
}
