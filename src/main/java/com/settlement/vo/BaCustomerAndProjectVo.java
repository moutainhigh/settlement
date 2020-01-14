package com.settlement.vo;

import lombok.Data;

@Data
public class BaCustomerAndProjectVo extends BaCustomerVo {
    private Integer projectId;
    private String projectName;
    private String projectCode;
}
