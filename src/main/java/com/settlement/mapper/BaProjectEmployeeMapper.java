package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.entity.BaProjectEmployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.ProjectEmployeeVo;
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
public interface BaProjectEmployeeMapper extends BaseMapper<BaProjectEmployee> {
    /** 获取未提交员工极路 */
    List<ProjectEmployeeVo> getNoSubmitProjectEmployeeList(@Param(value="projectEmployeeCo")ProjectEmployeeCo projectEmployeeCo, Page<ProjectEmployeeVo> page);

}
