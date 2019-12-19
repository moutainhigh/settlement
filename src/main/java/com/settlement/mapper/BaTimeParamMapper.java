package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaTimeParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaTimeParamVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 时间点参数表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-27
 */
@Repository
public interface BaTimeParamMapper extends BaseMapper<BaTimeParam> {
    /**根据id获得BaTimeParamVo**/
    BaTimeParamVo getTimeParamVoById(Map<String,Object> map);
    /**list**/
    List<BaTimeParamVo> listPageData(TimeParamCo timeParamCo, Page<BaTimeParamVo> page);
    /**根据projectId 和 时间点结参数类型返回结算日期**/
    String getTimeParamValueByProjectId(Map<String, Object> map);
    /** 根据projectId 和 时间点结参数类型 检查是否保存过相同类型时间点**/
    List<BaTimeParamVo> getTimeParamVoByProjectIdAndType(Map<String, Object> map);
}
