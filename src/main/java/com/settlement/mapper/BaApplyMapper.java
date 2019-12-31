package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ApplyCo;
import com.settlement.entity.BaApply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaApplyVo;
import com.settlement.vo.BaWorkAttendanceVo;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请修改员工信息表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-12-04
 */
public interface BaApplyMapper extends BaseMapper<BaApply> {

    List<BaApplyVo> getApplyWorkAttedances(ApplyCo applyCo, Page page);
    /**获得每个项目的申请修改的次数**/
    @Select("select " +
            " count(1)" +
            " from ba_apply a" +
            " left join ba_apply_attendance att on a.id = att.apply_id" +
            " left join ba_work_attendance wa on wa.id = att.attendance_id" +
            " where a.apply_type=#{applyType}" +
            " and wa.pg_id=#{projectId}" +
            " and  DATE_FORMAT(a.apply_time ,'%Y-%m' ) = #{applyTime}")
    Integer getApplyCountByProjectId(Map<String, Object> map);

    /**根据id 获得BaApplyVo*/
    BaApplyVo getApplyVoById(Map<String,Object> map);
    /**加载考勤审核列表**/
    List<BaApplyVo> listCheckWorkAttendancePageData(ApplyCo applyCo, Page<BaApplyVo> page);
    /**listApplyWorkAttendancelistPageData**/
    List<BaWorkAttendanceVo> listApplyWorkAttendancelistPageData(ApplyCo applyCo, Page<BaWorkAttendanceVo> page);
}
