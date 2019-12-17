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
    List<EmployeeVo> getNoSubmitEmployeeList(@Param(value="employeeCo") EmployeeCo employeeCo, Page<EmployeeVo> page);

}
