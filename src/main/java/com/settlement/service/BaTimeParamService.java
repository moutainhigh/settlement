package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaTimeParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaTimeParamVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 时间点参数表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-27
 */
public interface BaTimeParamService extends IService<BaTimeParam> {
    /**加载列表**/
    PageData listPageData(TimeParamCo timeParamCo);
    /**添加 **/
    Result addTimeParam(BaTimeParamVo baTimeParamVo);
    /**修改**/
    Result updateTimeParam(BaTimeParamVo baTimeParamVo);
    /**删除 **/
    Result deleteTimeParam(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**是否存在**/
    Result isExist(String code);
    /**获得当前年份**/
    List<String> getTimeYearValue();
    /**获得当前年份上个月和当前月份**/
    List<String> getTimeMonthValue();
    /** projectId获得结算考勤时间停止点**/
    String getStopTimeParam(Integer projectId);
    /**projectId 获得结算考勤时间完成点**/
    String getCompleteParam(Integer projectId);
    /**考勤结算时间点开始-完成区间**/
    Result judgeWorkattendanceDate(Integer projectId);
    /**考勤结算完成时间点**/
    Result judgeWorkattendanceCompleteTime(Integer projectId);
    /**考勤结算时间点**/
    Result judgeWorkattendanceStopTime(Integer projectId);
    /**根据id 获得BaTimeParamVo**/
    BaTimeParamVo getTimeParamVoById(Integer id);
    /**获得当前年月**/
    String getCurrentMonthYear();
    /**保存项目的时间点参数**/
    Result saveRelateProject(Integer[] projectIds, String timeParamId);
}
