package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.EmpApplyCheckCo;
import com.settlement.entity.BaEmpApplyCheck;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.EmpApplyCheckVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
public interface BaEmpApplyCheckMapper extends BaseMapper<BaEmpApplyCheck> {
    /** 员工申请页面数据 */
    List<EmpApplyCheckVo> getEmpApplyCheckList(@Param(value="empApplyCheckCo") EmpApplyCheckCo empApplyCheckCo, Page<EmpApplyCheckVo> page);

}
