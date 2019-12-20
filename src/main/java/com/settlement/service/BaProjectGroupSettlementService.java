package com.settlement.service;

import com.settlement.entity.BaProjectGroupSettlement;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupSettlementVo;

import java.util.List;

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
    /** 根据项目组ID查询结算负责人详细 */
    List<ProjectGroupSettlementVo> getProjectGroupSettlementDetailByPgId(Integer pgId);
}
