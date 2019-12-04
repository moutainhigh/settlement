package com.settlement.vo;

import com.settlement.entity.BaContract;
import lombok.Data;

@Data
public class BaContractVo extends BaContract {
    private String customerName;
    private String projectName;
    private String createUserName;
}
