package com.settlement.service.impl;

import com.settlement.entity.BaLevelPrice;
import com.settlement.mapper.BaLevelPriceMapper;
import com.settlement.service.BaLevelPriceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import com.settlement.vo.LevelPriceVo;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 级别单价表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-12-05
 */
@Service
@Transactional
public class BaLevelPriceServiceImpl extends ServiceImpl<BaLevelPriceMapper, BaLevelPrice> implements BaLevelPriceService {
    @Autowired
    private BaLevelPriceMapper baLevelPriceMapper;

    @Override
    public List<LevelPriceVo> getLevelPriceByPgId(Integer pgId) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("pgId", pgId);
        paramMap.put("delFlag", Const.DEL_FLAG_N);
        return baLevelPriceMapper.getLevelPriceByPgId(paramMap);
    }

    @Override
    public Result getLevelPriceById(Integer id) {
        Result r = new Result();
        r.setData(this.baLevelPriceMapper.getLevelPriceById(id));
        return r;
    }
}
