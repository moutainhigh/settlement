package com.settlement.mapper;

import com.settlement.entity.BaLevelPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.LevelPriceVo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 级别单价表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-12-05
 */
@Repository
public interface BaLevelPriceMapper extends BaseMapper<BaLevelPrice> {
    /** 根据项目ID，取得级别单价 */
    List<LevelPriceVo> getLevelPriceByPgId(Map<String, Object> map);
    /** 根据ID 取得级别单价 */
    LevelPriceVo getLevelPriceById(Integer id);

}
