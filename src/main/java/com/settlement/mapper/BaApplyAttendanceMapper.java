package com.settlement.mapper;

import com.settlement.entity.BaApplyAttendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 申请考勤关联 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@Repository
public interface BaApplyAttendanceMapper extends BaseMapper<BaApplyAttendance> {

    Integer insertBatch(List<BaApplyAttendance> list);
}
