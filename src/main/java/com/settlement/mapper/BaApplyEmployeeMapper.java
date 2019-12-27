package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.EmpApplyCo;
import com.settlement.entity.BaApplyEmployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.EmployeeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请员工关联 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@Repository
public interface BaApplyEmployeeMapper extends BaseMapper<BaApplyEmployee> {
    /** 批量保存员工申请费修改 */
    int insertEmpApplyBatch(@Param(value="applyEmps")  List<BaApplyEmployee> applyEmps);
    /** 根据申请ID 查询申请的员工信息 */
    List<EmployeeVo> getApplyEmpList(@Param(value="empApplyCo")EmpApplyCo empApplyCo, Page<EmployeeVo> page);
}
