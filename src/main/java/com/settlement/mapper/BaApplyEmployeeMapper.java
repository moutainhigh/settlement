package com.settlement.mapper;

import com.settlement.entity.BaApplyEmployee;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

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

}
