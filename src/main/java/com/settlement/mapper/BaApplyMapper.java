package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ApplyCo;
import com.settlement.entity.BaApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaApplyVo;

import java.util.List;

/**
 * <p>
 * 申请修改员工信息表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-12-04
 */
public interface BaApplyMapper extends BaseMapper<BaApply> {

    List<BaApplyVo> getApplyWorkAttedances(ApplyCo applyCo, Page page);
}
