package com.settlement.vo;

import com.settlement.entity.BaProjectGroupSettlement;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     项目组结算负责人Vo
 * </p>
 *
 * @auth admin
 * @date 2019-12-19
 */
@Data
public class ProjectGroupSettlementVo extends BaProjectGroupSettlement implements Serializable {

    private String settlementName;

}
