package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.controller.BaEmpLeavePgController;
import com.settlement.entity.*;
import com.settlement.mapper.*;
import com.settlement.service.BaEmpLeavePgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeavePgVo;
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
 * @since 2019-12-29
 */
@Service
@Transactional
public class BaEmpLeavePgServiceImpl extends ServiceImpl<BaEmpLeavePgMapper, BaEmpLeavePg> implements BaEmpLeavePgService {

    private static Logger logger = LoggerFactory.getLogger(BaEmpLeavePgServiceImpl.class);
    @Autowired
    private BaApplyEmployeeMapper baApplyEmployeeMapper;
    @Autowired
    private BaEmployeeMapper baEmployeeMapper;
    @Autowired
    private BaPgEmpMapper baPgEmpMapper;
    @Autowired
    private BaEmpApplyCheckMapper baEmpApplyCheckMapper;

    @Override
    public Result saveEmpLeavePg(EmpLeavePgVo empLeavePgVo) {
        logger.info("员工离场提交BaEmpLeavePgServiceImpl: submitEmpLeavePg");
        Result r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        try {
            // 保存ba_emp_leave_pg
            Date date = new Date();
            empLeavePgVo.setCreateTime(date);
            int ret = this.baseMapper.insert(empLeavePgVo);
            // 修改ba_apply_employee表update_status状态
            UpdateWrapper<BaApplyEmployee> updateWrapper = new UpdateWrapper<BaApplyEmployee>();
            updateWrapper.eq("id", empLeavePgVo.getApplyEmpId());
            BaApplyEmployee bae = new BaApplyEmployee();
            bae.setUpdateStatus(Const.EMP_APPLY_UPDATE_STATUS_F);
            int ret1 = baApplyEmployeeMapper.update(bae,updateWrapper);
            // 判断是否全部修改完成，全部修改完，更新ba_emp_apply_check 状态完成
            int applyId = baApplyEmployeeMapper.selectById(empLeavePgVo.getApplyEmpId()).getApplyId();
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
            // 判断离场时间是否大于等于当前时间，小于等于当前时间直接修改状态，大于当前时间不修改
            if (empLeavePgVo.getLeavePgTime().getTime() <= date.getTime()) {
                // 更新ba_employee entrance_status
                UpdateWrapper<BaEmployee> updateWrapper2 = new UpdateWrapper<BaEmployee>();
                updateWrapper2.eq("id", empLeavePgVo.getEmpId());
                BaEmployee be = new BaEmployee();
                be.setEntranceStatus(Const.ENTRANCE_STATUS_L);
                int ret3 = this.baEmployeeMapper.update(be, updateWrapper2);
                // 更新ba_pg_emp entrance_status
                UpdateWrapper<BaPgEmp> updateWrapper3 = new UpdateWrapper<BaPgEmp>();
                int pgId = this.baEmpApplyCheckMapper.selectById(applyId).getPgId();
                updateWrapper3.eq("pg_id", pgId);
                updateWrapper3.eq("emp_id", empLeavePgVo.getEmpId());
                BaPgEmp bge = new BaPgEmp();
                bge.setEntranceStatus(Const.ENTRANCE_STATUS_L);
                int ret4 = this.baPgEmpMapper.update(bge, updateWrapper3);
            }
            //  更新ba_employee表申请状态为null
            int ret5 = this.baEmployeeMapper.updateApplyUpdateStatusNull(empLeavePgVo.getEmpId());
        } catch (Exception e) {
            logger.error("员工离场提交BaEmpLeavePgServiceImpl: submitEmpLeavePg异常" + e.getMessage());
            e.printStackTrace();
            r.setCode(HttpResultEnum.CODE_500.getCode());
            r.setMsg(HttpResultEnum.CODE_500.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

  /*  @Override
    public BaEmpLeavePg getEmpLeavePgByApplyEmpId(Integer applyEmpId) {
        logger.info("根据applyEmpId: " + applyEmpId + " 查询项目员工离场信息BaEmpLeavePgServiceImpl: getEmpLeavePgByApplyEmpId");
        BaEmpLeavePg baEmpLeavePg = null;
        try {
            QueryWrapper<BaEmpLeavePg> queryWrapper = new QueryWrapper<BaEmpLeavePg>();
            queryWrapper.eq("apply_emp_id", applyEmpId);
            baEmpLeavePg = this.baseMapper.selectOne(queryWrapper);
        } catch (Exception e) {
            logger.error("查询项目员工离场信息BaEmpLeavePgServiceImpl: getEmpLeavePgByApplyEmpId异常" + e.getMessage());
            e.printStackTrace();
        }
        return baEmpLeavePg;
    }*/
}
