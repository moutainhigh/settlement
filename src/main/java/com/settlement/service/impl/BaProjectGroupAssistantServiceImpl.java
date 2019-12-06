package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.BaProjectGroupAssistant;
import com.settlement.mapper.BaProjectGroupAssistantMapper;
import com.settlement.service.BaProjectGroupAssistantService;
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
public class BaProjectGroupAssistantServiceImpl extends ServiceImpl<BaProjectGroupAssistantMapper, BaProjectGroupAssistant> implements BaProjectGroupAssistantService {
    @Autowired
    private BaProjectGroupAssistantMapper baProjectGroupAssistantMapper;

    @Override
    public Result batchInsert(Integer pgId, String assistantIds) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(),HttpResultEnum.CODE_500.getMessage());;
        try {
            List<BaProjectGroupAssistant> list = new ArrayList<BaProjectGroupAssistant>();
            if (assistantIds != null && !"".equals(assistantIds)) {
             // 先删除之前关联的助理
                this.baseMapper.delete(new QueryWrapper<BaProjectGroupAssistant>().eq("pg_id",pgId));
                BaProjectGroupAssistant baProjectGroupAssistant = null;
                String[] ids = assistantIds.split(",");
                for (int i = 0; i < ids.length; i++) {
                    baProjectGroupAssistant = new BaProjectGroupAssistant();
                    baProjectGroupAssistant.setPgId(pgId);
                    baProjectGroupAssistant.setAssistantId(Integer.valueOf(ids[i]));
                    list.add(baProjectGroupAssistant);
                }
                int ret1 = baProjectGroupAssistantMapper.insertBatch(list);
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

    /**
     * 根据项目id查询
     * @param projectId
     * @return
     */
    @Override
    public BaProjectGroupAssistant getBaProjectGroupAssistantByGroupId(Integer projectId) {
        QueryWrapper<BaProjectGroupAssistant> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pg_id",projectId);
        return  this.baProjectGroupAssistantMapper.selectOne(queryWrapper);
    }
}
