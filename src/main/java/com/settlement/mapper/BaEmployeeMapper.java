package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.EmployeeCo;
import com.settlement.entity.BaEmployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.EmployeeVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
@Repository
public interface BaEmployeeMapper extends BaseMapper<BaEmployee> {
    /** 获取未提交员工极路 */
    List<EmployeeVo> getEmployeeList(@Param(value="employeeCo") EmployeeCo employeeCo, Page<EmployeeVo> page);
    /** 批量更新员工入场状态 */
    int updateBatchEntranceStatusById(@Param(value="emps") List<BaEmployee> emps);
    /** 批量修改员工申请状态 */
    int updateApplyEmpStatusBatch(@Param(value="emps") List<BaEmployee> emps);
    /** 提交员工记录 */
    // List<EmployeeVo> getSubmitEmployeeList(@Param(value="employeeCo") EmployeeCo employeeCo, Page<EmployeeVo> page);
}
