package com.settlement.vo;

import com.settlement.entity.BaCustomer;
import lombok.Data;

/**
 * @description 客户vo
 *
 * @auth kun
 * @date 2019-11-20
 */
@Data
public class BaCustomerVo extends BaCustomer {
    private Integer deptId;
}
