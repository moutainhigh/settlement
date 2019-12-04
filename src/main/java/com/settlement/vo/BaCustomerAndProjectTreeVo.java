package com.settlement.vo;

import lombok.Data;

@Data
public class BaCustomerAndProjectTreeVo {
    private Integer id;
    private String title;
    private Integer parentId;
    private String checkArr;
}
