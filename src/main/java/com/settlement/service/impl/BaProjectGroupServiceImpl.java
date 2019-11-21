package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCo;
import com.settlement.entity.BaProjectGroup;
import com.settlement.mapper.BaProjectGroupMapper;
import com.settlement.service.BaProjectGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-20
 */
@Service
@Transactional
public class BaProjectGroupServiceImpl extends ServiceImpl<BaProjectGroupMapper, BaProjectGroup> implements BaProjectGroupService {

    @Override
    public PageData getProjectGroupList(ProjectGroupCo baProjectGroupCo) {
        Page<ProjectGroupVo> page = new Page<ProjectGroupVo>(baProjectGroupCo.getPage(), baProjectGroupCo.getLimit());
        page.setRecords(this.baseMapper.getProjectGroupList(baProjectGroupCo, page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    @Override
    public Result checkProjectGroupIsExist(String code) {
        QueryWrapper<BaProjectGroup> queryWrapper = new QueryWrapper<BaProjectGroup>();
        queryWrapper.eq("code",code);
        BaProjectGroup pg = this.baseMapper.selectOne(queryWrapper);
        Result r = new Result(HttpResultEnum.PG_CODE_1.getCode(), HttpResultEnum.PG_CODE_1.getMessage());
        if (pg == null) {
            r.setCode(HttpResultEnum.PG_CODE_0.getCode());
            r.setMsg(HttpResultEnum.PG_CODE_0.getMessage());
        }
        return r;
    }
}
