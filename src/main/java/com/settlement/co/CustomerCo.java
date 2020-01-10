package com.settlement.co;

import lombok.Data;

@Data
public class CustomerCo extends PageCo {
    private String keyword;
    private String delFlag;
    private String enabled;
    private String roleCode;
    private Integer userId;
}
