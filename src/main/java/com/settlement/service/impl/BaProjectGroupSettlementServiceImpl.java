package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.BaProjectGroupAssistant;
import com.settlement.entity.BaProjectGroupSettlement;
import com.settlement.mapper.BaProjectGroupSettlementMapper;
import com.settlement.service.BaProjectGroupSettlementService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@Service
@Transactional
public class BaProjectGroupSettlementServiceImpl extends ServiceImpl<BaProjectGroupSettlementMapper, BaProjectGroupSettlement> implements BaProjectGroupSettlementService {

    @Autowired
    private BaProjectGroupSettlementMapper baProjectGroupSettlementMapper;

    @Override
    public Result batchInsert(Integer pgId, String settlementIds) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(),HttpResultEnum.CODE_500.getMessage());;
        try {
            List<BaProjectGroupSettlement> list = new ArrayList<BaProjectGroupSettlement>();
            if (settlementIds != null && !"".equals(settlementIds)) {
                // 先删除之前关联的助理
                this.baseMapper.delete(new QueryWrapper<BaProjectGroupSettlement>().eq("pg_id",pgId));
                BaProjectGroupSettlement baProjectGroupSettlement = null;
                String[] ids = settlementIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    baProjectGroupSettlement = new BaProjectGroupSettlement();
                    baProjectGroupSettlement.setPgId(pgId);
                    baProjectGroupSettlement.setScId(Integer.valueOf(ids[i]));
                    list.add(baProjectGroupSettlement);
                }
                int ret1 = baProjectGroupSettlementMapper.insertBatch(list);
                if (ret1 > 0) {
                    r = new Result();
                    r.setCode(HttpResultEnum.CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
