package com.settlement.co;

import lombok.Data;

@Data
public class ApplyTransferCo extends PageCo{
    private String applyType;
    private String roleCode;
    private Integer applyId;
    private Integer checkUser;
    private String keyword;
    private String checkStatus;
}
