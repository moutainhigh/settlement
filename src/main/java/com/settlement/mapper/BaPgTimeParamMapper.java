package com.settlement.mapper;

import com.settlement.entity.BaPgTimeParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 项目组结算时间点参数表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-12-16
 */
@Repository
public interface BaPgTimeParamMapper extends BaseMapper<BaPgTimeParam> {
    /**批量插入**/
    Integer saveBatchs(List<BaPgTimeParam> baPgTimeParams);
}
