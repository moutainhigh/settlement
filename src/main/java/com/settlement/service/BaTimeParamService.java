package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaTimeParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

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
    Result addTimeParam(BaTimeParam baTimeParam);
    /**修改**/
    Result updateTimeParam(BaTimeParam baTimeParam);
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
    /**获得结算考勤时间停止点**/
    String getStopTimeParam();
    /**获得结算考勤时间完成点**/
    String getCompleteParam();

}
