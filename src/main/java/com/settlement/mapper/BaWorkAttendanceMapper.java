package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaWorkAttendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaWorkAttendanceVo;

import java.util.List;

/**
 * <p>
 * 考勤表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
public interface BaWorkAttendanceMapper extends BaseMapper<BaWorkAttendance> {
    /**根据项目组id获得当前项目组的员工考勤信息**/
    List<BaWorkAttendanceVo> getWorkAttendanceVoByProjectId(WorkAttendanceCo workAttendanceCo, Page<BaWorkAttendanceVo> page);
}
