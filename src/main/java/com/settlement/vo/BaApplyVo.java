package com.settlement.vo;

import com.settlement.entity.BaApply;
import lombok.Data;

@Data
public class BaApplyVo extends BaApply {
    private Integer[] workAttendanceIds;
    private String applyTypeContent;
    private String checkStatusContent;
    private String checkUserContent;
    private String applyUserContent;
}
