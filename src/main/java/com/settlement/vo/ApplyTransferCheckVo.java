package com.settlement.vo;

import com.settlement.entity.BaApplyTransfer;
import lombok.Data;

import java.io.Serializable;

@Data
public class ApplyTransferCheckVo implements Serializable {
    private String checkResult;
    private String checkRemark;
    private Integer applyId;
    private String applyType;
}
