package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCo;
import com.settlement.entity.*;
import com.settlement.mapper.*;
import com.settlement.service.BaProjectGroupAssistantService;
import com.settlement.service.BaProjectGroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.HttpStateEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaApplyTransferVo;
import com.settlement.vo.ProjectGroupVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    private static Logger logger = LoggerFactory.getLogger(BaProjectGroupServiceImpl.class);
    @Autowired
    private BaProjectGroupAssistantMapper baProjectGroupAssistantMapper;
    @Autowired
    private BaProjectGroupCheckMapper baProjectGroupCheckMapper;
    @Autowired
    private BaProjectGroupMapper baProjectGroupMapper;
    @Autowired
    private BaProjectGroupSettlementMapper baProjectGroupSettlementMapper;
    @Autowired
    private BaPgEmpMapper baPgEmpMapper;
    @Autowired
    private BaApplyTransferMapper baApplyTransferMapper;
    @Autowired
    private BaTransferMapper baTransferMapper;

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
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        BaProjectGroup pg = this.baseMapper.selectOne(queryWrapper);
        Result r = new Result(HttpResultEnum.PG_CODE_1.getCode(), HttpResultEnum.PG_CODE_1.getMessage());
        if (pg == null) {
            r.setCode(HttpResultEnum.PG_CODE_0.getCode());
            r.setMsg(HttpResultEnum.PG_CODE_0.getMessage());
        }
        return r;
    }

    @Override
    public Result saveProjectGroup(ProjectGroupVo projectGroupVo) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            int ret = this.baseMapper.insert(projectGroupVo);
            if (ret > 0) {
                r = new Result();
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result addProjectGroup(ProjectGroupVo projectGroupVo) {
        Result  r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            int ret = this.baseMapper.insert(projectGroupVo);
            BaProjectGroupCheck bgc = new BaProjectGroupCheck();
            bgc.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
            bgc.setApplyTime(new Date());
            bgc.setPgId(projectGroupVo.getId());
            bgc.setCheckUserId(projectGroupVo.getCheckUserId());
            int ret1 = baProjectGroupCheckMapper.insert(bgc);
            if (ret > 0 && ret1 > 0) {
                r = new Result();
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    @Override
    public Result updateProjectGroup(ProjectGroupVo projectGroupVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaProjectGroup> updateWrapper = new UpdateWrapper<BaProjectGroup>();
            updateWrapper.set("pg_name",projectGroupVo.getPgName());
            updateWrapper.set("code", projectGroupVo.getCode());
            updateWrapper.set("check_user_id", projectGroupVo.getCheckUserId());
            updateWrapper.eq("id",projectGroupVo.getId());
            int ret = this.baProjectGroupMapper.update(projectGroupVo, updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result updateAndSubmitProjectGroup(ProjectGroupVo projectGroupVo) {
        Result  r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaProjectGroup> updateWrapper = new UpdateWrapper<BaProjectGroup>();
            updateWrapper.set("pg_name",projectGroupVo.getPgName());
            updateWrapper.set("code", projectGroupVo.getCode());
            updateWrapper.set("check_user_id", projectGroupVo.getCheckUserId());
            updateWrapper.set("check_status",Const.CHECK_STATUS_NO_CHECK);
            updateWrapper.eq("id",projectGroupVo.getId());
            int ret = this.baProjectGroupMapper.update(projectGroupVo, updateWrapper);
            BaProjectGroupCheck bgc = new BaProjectGroupCheck();
            bgc.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
            bgc.setApplyTime(new Date());
            bgc.setPgId(projectGroupVo.getId());
            bgc.setCheckUserId(projectGroupVo.getCheckUserId());
            int ret1 = baProjectGroupCheckMapper.insert(bgc);
            if (ret > 0 && ret1 > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result deleteProjectGroup(Integer id) {
        Result  r = new Result(HttpResultEnum.DEL_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaProjectGroup> updateWrapper = new UpdateWrapper<BaProjectGroup>();
            updateWrapper.set("del_flag",Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            int ret = this.baseMapper.update(new BaProjectGroup().setId(id), updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public ProjectGroupVo getProjectGroupAssistantById(Integer id) {
        BaProjectGroup pg = this.baseMapper.selectById(id);
        ProjectGroupVo pgv = new ProjectGroupVo();
        pgv.setId(pg.getId());
        pgv.setPgName(pg.getPgName());
        pgv.setCode(pg.getCode());
        List<BaProjectGroupAssistant> assistants =  baProjectGroupAssistantMapper.selectList(new QueryWrapper<BaProjectGroupAssistant>().eq("pg_id",id));
        String assistant = "";
        if (assistants != null && assistants.size() > 0) {
            for (int i = 0; i < assistants.size(); i++) {
                if (i != assistants.size() - 1) {
                    assistant += assistants.get(i).getAssistantId() + ",";
                } else {
                    assistant += assistants.get(i).getAssistantId();
                }
            }
        }
        pgv.setAssistants(assistant);
        return pgv;
    }

    @Override
    public ProjectGroupVo getProjectGroupSettlementById(Integer id) {
        BaProjectGroup pg = this.baseMapper.selectById(id);
        ProjectGroupVo pgv = new ProjectGroupVo();
        pgv.setId(pg.getId());
        pgv.setPgName(pg.getPgName());
        pgv.setCode(pg.getCode());
        List<BaProjectGroupSettlement> settlements =  baProjectGroupSettlementMapper.selectList(new QueryWrapper<BaProjectGroupSettlement>().eq("pg_id",id));
        String settlement = "";
        if (settlements != null && settlements.size() > 0) {
            for (int i = 0; i < settlements.size(); i++) {
                if (i != settlements.size() - 1) {
                    settlement += settlements.get(i).getScId() + ",";
                } else {
                    settlement += settlements.get(i).getScId();
                }
            }
        }
        pgv.setSettlements(settlement);
        return pgv;
    }

    /**
     * 根据customerId获得项目组
     * @param customerId
     * @return
     */
    @Override
    public Result getGroupsByCustomerId(Integer customerId) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        QueryWrapper<BaProjectGroup> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id",customerId);
        List<BaProjectGroup> baProjectGroups = this.baseMapper.selectList(queryWrapper);
        if(baProjectGroups!=null) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(baProjectGroups);
        }
        return r;
    }

    @Override
    public Result checkPgStart(Integer id) {
        QueryWrapper<BaProjectGroupAssistant> queryWrapperPgAssistant = new QueryWrapper<BaProjectGroupAssistant>();
        queryWrapperPgAssistant.eq("pg_id",id);
        int ret = baProjectGroupAssistantMapper.selectCount(queryWrapperPgAssistant);
        QueryWrapper<BaProjectGroupSettlement> queryWrapperPgSettlement = new QueryWrapper<BaProjectGroupSettlement>();
        queryWrapperPgSettlement.eq("pg_id",id);
        int ret1 = this.baProjectGroupSettlementMapper.selectCount(queryWrapperPgSettlement);
        Result r = new Result();
        if (ret == 0) {
            r.setCode(HttpResultEnum.PG_ASSISTANT_0.getCode());
            r.setMsg(HttpResultEnum.PG_ASSISTANT_0.getMessage());
        } else if (ret1 == 0) {
            r.setCode(HttpResultEnum.PG_SETTLEMENT_0.getCode());
            r.setMsg(HttpResultEnum.PG_SETTLEMENT_0.getMessage());
        } else if (ret > 0 & ret1 > 0) {
            r.setCode(HttpResultEnum.CODE_200.getCode());
            r.setMsg(HttpResultEnum.CODE_200.getMessage());
        }
        return r;
    }

    @Override
    public Result updatePgStart(Integer id) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(), HttpResultEnum.CODE_500.getMessage());
        try {
            UpdateWrapper<BaProjectGroup> updateWrapper = new UpdateWrapper<BaProjectGroup>();
            updateWrapper.eq("id",id);
            BaProjectGroup bpg = new BaProjectGroup();
            bpg.setEnabled(Const.ENABLED_Y);
            int ret = this.baseMapper.update(bpg, updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.CODE_200.getCode());
                r.setMsg(HttpResultEnum.CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result updatePgStopById(Integer id) {
        logger.info("项目组停用BaProjectGroupServiceImpl: updatePgStopById, id: " + id + "");
        Result r = new Result();
        // 检查是否存在入场员工，有入场员工不得停用
        try {
            QueryWrapper<BaPgEmp> queryWrapper = new QueryWrapper<BaPgEmp>();
            queryWrapper.eq("pg_id", id);
            queryWrapper.eq("entrance_status",Const.ENTRANCE_STATUS_I);
            int count = this.baPgEmpMapper.selectCount(queryWrapper);
            if (count == 0) {
                // 更新停用
                UpdateWrapper<BaProjectGroup> updateWrapper = new UpdateWrapper<BaProjectGroup>();
                updateWrapper.eq("id", id);
                BaProjectGroup bg = new BaProjectGroup();
                bg.setEnabled(Const.ENABLED_N);
                int ret = this.baseMapper.update(bg, updateWrapper);
                if (ret > 0) {
                    r.setCode(HttpResultEnum.CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CODE_200.getMessage());
                }
            } else {
                r.setCode(HttpResultEnum.PG_STOP_CODE_500.getCode());
                r.setMsg(HttpResultEnum.PG_STOP_CODE_500.getMessage());
            }
        } catch (Exception e) {
            logger.error("项目组停用BaProjectGroupServiceImpl: updatePgStopById异常");
            r.setCode(HttpResultEnum.CODE_500.getCode());
            r.setMsg(HttpResultEnum.CODE_500.getMessage());
        }
        return r;
    }

    /**
     * 项目移交
     * @param baApplyTransferVo
     * @return
     */
    @Override
    public Result projectApplyTransfer(BaApplyTransferVo baApplyTransferVo) {
        Result r = new Result(HttpResultEnum.TRANS_CODE_500.getCode(),HttpResultEnum.TRANS_CODE_500.getMessage());
        try{
            baApplyTransferVo.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
            baApplyTransferVo.setApplyTime(new Date());
            baApplyTransferVo.setApplyType(Const.APPLY_TRANSFER_PROJECT);
            baApplyTransferVo.setCheckUser(baApplyTransferVo.getCheckUser());
            Integer ret = baApplyTransferMapper.insert(baApplyTransferVo);
            if(ret!=null && ret >0) {
                List<BaTransfer> baTransfers = new ArrayList<>();
                for(Integer id : baApplyTransferVo.getIds()) {
                    BaTransfer baTransfer = new BaTransfer();
                    baTransfer.setApplyTransferId(baApplyTransferVo.getId());
                    baTransfer.setTransferId(id);
                    baTransfers.add(baTransfer);
                }
                Integer ret2 = baTransferMapper.insertBatch(baTransfers);
                if(ret2!=null && ret2 >0) {
                    r.setMsg(HttpResultEnum.TRANS_CODE_200.getMessage());
                    r.setCode(HttpResultEnum.TRANS_CODE_200.getCode());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
