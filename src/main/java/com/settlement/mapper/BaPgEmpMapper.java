package com.settlement.mapper;

import com.settlement.entity.BaEmployee;
import com.settlement.entity.BaPgEmp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-12-11
 */
@Repository
public interface BaPgEmpMapper extends BaseMapper<BaPgEmp> {

    /** 批量更新员工提交状态 */
    int updateEmpSubStatusBatchById(@Param("pgEmps") List<BaPgEmp> pgEmps);

}
