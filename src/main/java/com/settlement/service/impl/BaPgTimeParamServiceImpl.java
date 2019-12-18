package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.BaPgTimeParam;
import com.settlement.mapper.BaPgTimeParamMapper;
import com.settlement.mapper.BaProjectGroupMapper;
import com.settlement.service.BaPgTimeParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目组结算时间点参数表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-16
 */
@Service
public class BaPgTimeParamServiceImpl extends ServiceImpl<BaPgTimeParamMapper, BaPgTimeParam> implements BaPgTimeParamService {
    @Autowired
    private BaProjectGroupMapper baProjectGroupMapper;
    /**
     * 根据timeParamId 回显选中的项目
     * @param timeParamId
     * @return
     */
    @Override
    public Result getCheckedValueByTimeParamId(Integer timeParamId) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        //根据timeParamId查询
        QueryWrapper<BaPgTimeParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("t_p_id",timeParamId);
        List<BaPgTimeParam> baPgTimeParams = this.baseMapper.selectList(queryWrapper);
        String pgIdChecked="";
        Map<Integer,Object> map = new HashMap<>();
        for (BaPgTimeParam baPgTimeParam:baPgTimeParams) {
            Integer projectId=baPgTimeParam.getPgId();
            pgIdChecked+=projectId+",";
            Integer pid = baProjectGroupMapper.selectById(projectId).getCustomerId();
            if(map.containsKey(pid)) {
                continue;
            } else {
                map.put(pid,pid);
                pgIdChecked+=pid+",";
            }
        }
        if(pgIdChecked.length()>0) {
            pgIdChecked=pgIdChecked.substring(0,pgIdChecked.length()-1);
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(pgIdChecked);
        }
        return r;
    }
}
