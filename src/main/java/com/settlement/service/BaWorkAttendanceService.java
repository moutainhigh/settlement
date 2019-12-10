package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaWorkAttendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaWorkAttendanceVo;
import io.swagger.models.auth.In;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 考勤表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
public interface BaWorkAttendanceService extends IService<BaWorkAttendance> {
    /**加载列表**/
    PageData listPageData(WorkAttendanceCo workAttendanceCo);
    /**添加 **/
    Result add(BaWorkAttendanceVo baWorkAttendanceVo);
    /**修改**/
    Result update(BaWorkAttendanceVo baWorkAttendanceVo);
    /**根据id查询**/
    BaWorkAttendanceVo getBaWorkAttendanceVoById(Integer id);
    /**根据年月查询考勤记录**/
    List<BaWorkAttendance> getBaWorkAttendanceVoByNextMonth(Map<String, String> map);
}
