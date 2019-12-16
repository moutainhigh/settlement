package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCheckCo;
import com.settlement.entity.BaProjectGroup;
import com.settlement.entity.BaProjectGroupCheck;
import com.settlement.mapper.BaProjectGroupCheckMapper;
import com.settlement.mapper.BaProjectGroupMapper;
import com.settlement.service.BaProjectGroupCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.service.BaProjectGroupService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * <p>
 * 项目审核表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@Service
@Transactional
public class BaProjectGroupCheckServiceImpl extends ServiceImpl<BaProjectGroupCheckMapper, BaProjectGroupCheck> implements BaProjectGroupCheckService {
    @Autowired
    private BaProjectGroupCheckMapper baProjectGroupCheckMapper;
    @Autowired
    private BaProjectGroupMapper baProjectGroupMapper;

    @Override
    public PageData getPgCheckPageList(ProjectGroupCheckCo projectGroupCheckCo) {
        Page<ProjectGroupCheckVo> page = new Page<ProjectGroupCheckVo>(projectGroupCheckCo.getPage(), projectGroupCheckCo.getLimit());
        page.setRecords(this.baseMapper.getProjectGroupCheckPageList(projectGroupCheckCo, page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    @Override
    public ProjectGroupCheckVo getPgCheckById(Integer id) {
        return this.baProjectGroupCheckMapper.getPgCheckById(id);
    }

    @Override
    public Result updatePgCheck(ProjectGroupCheckVo projectGroupCheckVo) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(), HttpResultEnum.CODE_500.getMessage());
        // 更新项目组审核表
        try {
            UpdateWrapper<BaProjectGroupCheck> updateWrapper = new UpdateWrapper<BaProjectGroupCheck>();
            BaProjectGroupCheck bjgc = new BaProjectGroupCheck();
            bjgc.setId(projectGroupCheckVo.getId());
            bjgc.setCheckTime(new Date());
            bjgc.setRemark(projectGroupCheckVo.getRemark());
            bjgc.setCheckStatus(Const.CHECK_RESULT_PASS_CODE.equals(projectGroupCheckVo.getCheckResult()) ? Const.CHECK_STATUS_CHECK_PASS: Const.CHECK_STATUS_CHECK_NOPASS);
            /*updateWrapper.set("check_time",bjgc.getCheckTime());
            updateWrapper.set("remark",bjgc.getRemark());
            updateWrapper.set("check_status",bjgc.getCheckStatus());*/
            updateWrapper.eq("id",bjgc.getId());
            int ret = this.baseMapper.update(bjgc,updateWrapper);
            // 更新项目组表审核状态
            UpdateWrapper<BaProjectGroup> updateWrapper1 = new UpdateWrapper<BaProjectGroup>();
            BaProjectGroup bpg = new BaProjectGroup();
            bpg.setId(projectGroupCheckVo.getPgId());
            bpg.setCheckStatus(bjgc.getCheckStatus());
            bpg.setCustomerId(projectGroupCheckVo.getCustomerId());
            /*updateWrapper1.set("check_status",bpg.getCheckStatus());
            updateWrapper1.set("customer_id", bpg.getCustomerId());*/
            updateWrapper1.eq("id",bpg.getId());
            int ret1 = this.baProjectGroupMapper.update(bpg, updateWrapper1);
            if (ret > 0 && ret1 > 0) {
                r.setCode(HttpResultEnum.CODE_200.getCode());
                r.setMsg(HttpResultEnum.CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
