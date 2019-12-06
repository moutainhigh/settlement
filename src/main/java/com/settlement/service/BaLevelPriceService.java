package com.settlement.service;

import com.settlement.entity.BaLevelPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.LevelPriceVo;

import java.util.List;

/**
 * <p>
 * 级别单价表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-12-05
 */
public interface BaLevelPriceService extends IService<BaLevelPrice> {
    /** 根据项目组ID 查询关联的级别单价 */
    List<LevelPriceVo> getLevelPriceByPgId(Integer pgId);
    /** 根据ID 查询级别单价 */
    Result getLevelPriceById(Integer id);
}
