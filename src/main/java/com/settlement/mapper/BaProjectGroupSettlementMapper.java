package com.settlement.mapper;

import com.settlement.entity.BaProjectGroupAssistant;
import com.settlement.entity.BaProjectGroupSettlement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.ProjectGroupSettlementVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@Repository
public interface BaProjectGroupSettlementMapper extends BaseMapper<BaProjectGroupSettlement> {

    /**
     * <p>
     * @description 批量插入
     * </p>
     *
     * @auth admin
     * @date 2019-11-22
     * @param list
     * @return
     */
    int insertBatch(List<BaProjectGroupSettlement> list);

    /**
     * @description 项目组ID查询结算负责人
     *
     * @auth admin
     * @date 2019-12-19
     * @param pgId
     * @return
     */
    List<ProjectGroupSettlementVo> selectProjectSettlementByPgId(Integer pgId);
}
