package com.settlement.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApplyAndCheckUserVo {
    private Integer applyUserId;
    private Integer checkUserId;
    private String checkUserName;
    private Integer[] ids;
}
