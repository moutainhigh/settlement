package com.settlement.service;

import com.settlement.entity.BaProjectGroupSettlement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
public interface BaProjectGroupSettlementService extends IService<BaProjectGroupSettlement> {
    /** 批量插入项目组结算负责人 */
    Result batchInsert(Integer pgId, String settlementIds);
}
