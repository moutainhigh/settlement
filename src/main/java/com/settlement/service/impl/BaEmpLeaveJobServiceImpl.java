package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.entity.*;
import com.settlement.mapper.*;
import com.settlement.service.BaEmpLeaveJobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeaveJobVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-30
 */
@Service
@Transactional
public class BaEmpLeaveJobServiceImpl extends ServiceImpl<BaEmpLeaveJobMapper, BaEmpLeaveJob> implements BaEmpLeaveJobService {

    private static Logger logger = LoggerFactory.getLogger(BaEmpLeaveJobServiceImpl.class);
    @Autowired
    private BaApplyEmployeeMapper baApplyEmployeeMapper;
    @Autowired
    private BaEmployeeMapper baEmployeeMapper;
    @Autowired
    private BaPgEmpMapper baPgEmpMapper;
    @Autowired
    private BaEmpApplyCheckMapper baEmpApplyCheckMapper;

   @Override
    public Result saveEmpLeaveJob(EmpLeaveJobVo empLeaveJobVo) {
        logger.info("员工离场BaEmpLeaveJobServiceImpl: saveEmpLeaveJob");
        Result  r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        try {
            // 保存ba_emp_leave_job表
            Date date = new Date();
            empLeaveJobVo.setCreateTime(date);
            int ret = this.baseMapper.insert(empLeaveJobVo);
            // 更新ba_apply_employee表， udpate_status状态
            UpdateWrapper<BaApplyEmployee> updateWrapper = new UpdateWrapper<BaApplyEmployee>();
            updateWrapper.eq("id", empLeaveJobVo.getApplyEmpId());
            BaApplyEmployee bae = new BaApplyEmployee();
            bae.setUpdateStatus(Const.EMP_APPLY_UPDATE_STATUS_F);
            int ret1 = baApplyEmployeeMapper.update(bae,updateWrapper);
            // 判断ba_apply_employee表update_statas状态是否全部修改完成，修改完成，更新ba_emp_apply_check表end_status状态
            int applyId = baApplyEmployeeMapper.selectById(empLeaveJobVo.getApplyEmpId()).getApplyId();
            QueryWrapper<BaApplyEmployee> queryWrapperCount = new QueryWrapper<BaApplyEmployee>();
            queryWrapperCount.eq("apply_id", applyId);
            int total = baApplyEmployeeMapper.selectCount(queryWrapperCount);
            queryWrapperCount.eq("update_status",Const.EMP_APPLY_UPDATE_STATUS_F);
            int updateStatusFCount = baApplyEmployeeMapper.selectCount(queryWrapperCount);
            int ret2 = 0;
            if (total == updateStatusFCount) {
                UpdateWrapper<BaEmpApplyCheck> updateWrapper1 = new UpdateWrapper<BaEmpApplyCheck>();
                updateWrapper1.eq("id", applyId);
                BaEmpApplyCheck beac = new BaEmpApplyCheck();
                beac.setEndStatus(Const.END_STATUS_END_CODE);
                ret2 = this.baEmpApplyCheckMapper.update(beac, updateWrapper1);
            }
            // 判断离职日期是否等于当前时间，小于等于更新ba_employee、ba_pg_emp表, job_status， entrance_status状态
            if (empLeaveJobVo.getLeaveJobTime().getTime() <= date.getTime()) {
                // 更新ba_employee entrance_status
                UpdateWrapper<BaEmployee> updateWrapper2 = new UpdateWrapper<BaEmployee>();
                updateWrapper2.eq("id", empLeaveJobVo.getEmpId());
                BaEmployee be = new BaEmployee();
                be.setEntranceStatus(Const.ENTRANCE_STATUS_L);
                be.setJobStatus(Const.JOB_STATUS_L);
                int ret3 = this.baEmployeeMapper.update(be, updateWrapper2);
                // 更新ba_pg_emp entrance_status
                UpdateWrapper<BaPgEmp> updateWrapper3 = new UpdateWrapper<BaPgEmp>();
                int pgId = this.baEmpApplyCheckMapper.selectById(applyId).getPgId();
                updateWrapper3.eq("pg_id", pgId);
                updateWrapper3.eq("emp_id", empLeaveJobVo.getEmpId());
                BaPgEmp bge = new BaPgEmp();
                bge.setEntranceStatus(Const.ENTRANCE_STATUS_L);
                int ret4 = this.baPgEmpMapper.update(bge, updateWrapper3);
            }
            // 更新ba_employee表, apply_update_status状态
            int ret5 = this.baEmployeeMapper.updateApplyUpdateStatusNull(empLeaveJobVo.getEmpId());
        } catch (Exception e) {
            logger.error("员工离职BaEmpLeaveJobServiceImpl: saveEmpLeaveJob异常" + e.getMessage());
            e.printStackTrace();
            r.setCode(HttpResultEnum.CODE_500.getCode());
            r.setMsg(HttpResultEnum.CODE_500.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /*
    @Override
    public BaEmpLeaveJob getEmpLeaveJobByApplyEmpId(Integer applyEmpId) {
        logger.info("根据申请员工IDapplyEmpId: " + applyEmpId + " 查询员工离职BaEmpLeaveJobServiceImpl: getEmpLeaveJobByApplyEmpId");
        BaEmpLeaveJob belj = null;
        try {
            belj = null;
            QueryWrapper<BaEmpLeaveJob> queryWrapper = new QueryWrapper<BaEmpLeaveJob>();
            queryWrapper.eq("apply_emp_id", applyEmpId);
            belj = this.baseMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            logger.error("查询员工离职BaEmpLeaveJobServiceImpl: getEmpLeaveJobByApplyEmpId异常" + e.getMessage());
            e.printStackTrace();
        }
        return belj;
    }*/
}
