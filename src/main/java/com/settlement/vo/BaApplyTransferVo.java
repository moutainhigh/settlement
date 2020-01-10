package com.settlement.vo;

import com.settlement.entity.BaApplyTransfer;
import lombok.Data;

@Data
public class BaApplyTransferVo  extends BaApplyTransfer {
    private Integer[] ids;
    private String applyUserContent;
    private String applyTypeContent;
    private String checkStatusContent;
    private String recieveUserContent;
    private String customerOrProjectName;
}
