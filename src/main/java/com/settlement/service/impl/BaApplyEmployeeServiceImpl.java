package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCo;
import com.settlement.entity.BaApplyEmployee;
import com.settlement.entity.BaEmpApplyCheck;
import com.settlement.entity.BaEmployee;
import com.settlement.mapper.BaApplyEmployeeMapper;
import com.settlement.mapper.BaEmpApplyCheckMapper;
import com.settlement.mapper.BaEmployeeMapper;
import com.settlement.service.BaApplyEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.service.BaEmployeeService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmployeeVo;
import com.settlement.vo.ProjectGroupVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请员工关联 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@Service
@Transactional
public class BaApplyEmployeeServiceImpl extends ServiceImpl<BaApplyEmployeeMapper, BaApplyEmployee> implements BaApplyEmployeeService {

    private static Logger logger = LoggerFactory.getLogger(BaApplyEmployeeServiceImpl.class);
    @Autowired
    private BaEmployeeMapper baEmployeeMapper;
    @Autowired
    private BaEmpApplyCheckMapper baEmpApplyCheckMapper;

    @Override
    public PageData getApplyEmpPageList(EmpApplyCo empApplyCo) {
        empApplyCo.setLevelModeF(Const.LEVEL_MODE_F);
        empApplyCo.setLevelModeH(Const.LEVEL_MODE_H);
        empApplyCo.setUpdateStatus(Const.EMP_APPLY_UPDATE_STATUS_A);
        Page<EmployeeVo> page = new Page<EmployeeVo>(empApplyCo.getPage(), empApplyCo.getLimit());
        page.setRecords(this.baseMapper.getApplyEmpList(empApplyCo, page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    @Override
    public Result updateApplyEmp(EmployeeVo employeeVo, Integer applyEmpId) {
        logger.info("员工申请修改-编辑 BaApplyEmployeeServiceImpl：updateApplyEmp ");
        Result r = new Result(HttpResultEnum.EDIT_CODE_200.getCode(), HttpResultEnum.EDIT_CODE_200.getMessage());
        try {
            // 更新员工表
            UpdateWrapper<BaEmployee> updateWrapper = new UpdateWrapper<BaEmployee>();
            updateWrapper.eq("id", employeeVo.getId());
            int ret = this.baEmployeeMapper.update(employeeVo,updateWrapper);
            // 更新ba_apply_employee表, update_status状态
            UpdateWrapper<BaApplyEmployee> updateWrapper1 = new UpdateWrapper<BaApplyEmployee>();
            updateWrapper1.eq("id", applyEmpId);
            BaApplyEmployee bae = new BaApplyEmployee();
            bae.setUpdateStatus(Const.EMP_APPLY_UPDATE_STATUS_F);
            int ret1 = this.baseMapper.update(bae, updateWrapper1);
            // 判断ba_apply_employee表udpate_status状态是否更新完成，如果完成，更新ba_emp_apply_check表end_status
            int applyId = this.baseMapper.selectById(applyEmpId).getApplyId();
            QueryWrapper<BaApplyEmployee> queryWrapperCount = new QueryWrapper<BaApplyEmployee>();
            queryWrapperCount.eq("apply_id", applyId);
            int total = this.baseMapper.selectCount(queryWrapperCount);
            queryWrapperCount.eq("update_status",Const.EMP_APPLY_UPDATE_STATUS_F);
            int updateStatusFCount = this.baseMapper.selectCount(queryWrapperCount);
            int ret2 = 0;
            if (total == updateStatusFCount) {
                UpdateWrapper<BaEmpApplyCheck> updateWrapper2 = new UpdateWrapper<BaEmpApplyCheck>();
                updateWrapper2.eq("id", applyId);
                BaEmpApplyCheck beac = new BaEmpApplyCheck();
                beac.setEndStatus(Const.END_STATUS_END_CODE);
                ret2 = this.baEmpApplyCheckMapper.update(beac, updateWrapper2);
            }
            int ret3 = this.baEmployeeMapper.updateApplyUpdateStatusNull(employeeVo.getId());
        } catch (Exception e) {
            logger.error("员工申请修改-编辑 BaApplyEmployeeServiceImpl：updateApplyEmp异常" + e.getMessage());
            e.printStackTrace();
            r.setCode(HttpResultEnum.EDIT_CODE_500.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_500.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
