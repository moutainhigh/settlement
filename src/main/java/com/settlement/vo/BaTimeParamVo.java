package com.settlement.vo;

import com.settlement.entity.BaTimeParam;
import lombok.Data;

@Data
public class BaTimeParamVo extends BaTimeParam {
    private Integer customerId;
    private String customerName;
    private Integer projectId;
    private String projectName;
}
