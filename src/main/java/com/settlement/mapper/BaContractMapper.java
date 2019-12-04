package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ContractCo;
import com.settlement.entity.BaContract;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaContractVo;

import java.util.List;

/**
 * <p>
 * 合同管理表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
public interface BaContractMapper extends BaseMapper<BaContract> {
    /**根据id获得BaContractVo**/
    BaContractVo getBaContractVoById(Integer id);
    /**获得BaContractVo列表**/
    List<BaContractVo> getBaContractVos(ContractCo contractCo, Page<BaContractVo> page);
}
