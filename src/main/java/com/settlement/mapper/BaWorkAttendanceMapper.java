package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaWorkAttendance;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaWorkAttendanceVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

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
    /**根据id获得当前项目组的员工考勤信息**/
    BaWorkAttendanceVo getBaWorkAttendanceVoById(Map<String,Object> map);
    /**根据年月查询考勤记录**/
    List<BaWorkAttendance> getBaWorkAttendanceVoByNextMonth(Map<String,String> map);
    /***根据申请修改的考勤id获得要修改的数据*/
    List<BaWorkAttendanceVo> getWorkAttendanceByApplyId(WorkAttendanceCo workAttendanceCo, Page<BaWorkAttendanceVo> page);
}
