package com.settlement.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCo;
import com.settlement.entity.BaApplyEmployee;
import com.settlement.entity.BaEmpApplyCheck;
import com.settlement.entity.BaEmployee;
import com.settlement.entity.SysUser;
import com.settlement.mapper.BaApplyEmployeeMapper;
import com.settlement.mapper.BaEmpApplyCheckMapper;
import com.settlement.mapper.BaEmployeeMapper;
import com.settlement.service.BaApplyEmployeeService;
import com.settlement.service.BaEmpApplyCheckService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.service.BaEmployeeService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmpApplyCheckVo;
import com.settlement.vo.ProjectGroupVo;
import org.apache.shiro.SecurityUtils;
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
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@Service
@Transactional
public class BaEmpApplyCheckServiceImpl extends ServiceImpl<BaEmpApplyCheckMapper, BaEmpApplyCheck> implements BaEmpApplyCheckService {

    private static Logger logger = LoggerFactory.getLogger(BaEmpApplyCheckServiceImpl.class);
    @Autowired
    private BaApplyEmployeeMapper baApplyEmployeeMapper;
    @Autowired
    private BaEmployeeMapper baEmployeeMapper;

    @Override
    public Result insertEmpApply(EmpApplyCheckVo empApplyCheckVo, String empIds) {
        logger.info("员工申请批量保存service处理");
        Result r = new Result();
        try {
            if (empIds != null && !"".equals(empIds)) {
                // 新增员工申请修改
                BaEmpApplyCheck beac = new BaEmpApplyCheck();
                beac.setApplyTime(new Date());
                SysUser applyUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
                beac.setApplyUserId(applyUser.getId());
                beac.setCheckUserId(empApplyCheckVo.getCheckUserId());
                beac.setApplyRemark(empApplyCheckVo.getApplyRemark());
                beac.setPgId(empApplyCheckVo.getPgId());
                beac.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
                int ret = this.baseMapper.insert(beac);
                // 新增员工申请修改关联表
                List<BaApplyEmployee> list = new ArrayList<BaApplyEmployee>();
                List<BaEmployee> empList = new ArrayList<BaEmployee>();
                BaApplyEmployee bae = null;
                BaEmployee be = null;
                Date d = new Date();
                String[] empArr = empIds.split(",");
                for (int i = 0; i < empArr.length; i++) {
                    int empId = Integer.valueOf(empArr[i]);
                    bae = new BaApplyEmployee();
                    bae.setApplyId(beac.getId());
                    bae.setEmpId(empId);
                    bae.setUpdateStatus(Const.EMP_APPLY_UPDATE_STATUS_A);
                    bae.setOperTime(d);
                    list.add(bae);
                    be = new BaEmployee();
                    be.setId(empId);
                    be.setApplyUpdateStatus(Const.EMP_APPLY_UPDATE_STATUS_A);
                    be.setUpdateTime(d);
                    empList.add(be);
                }
                int ret1 = baApplyEmployeeMapper.insertEmpApplyBatch(list);
                // 修改员工表申请修改状态\
                int ret2 = baEmployeeMapper.updateApplyEmpStatusBatch(empList);
                if (ret > 0 && ret1 > 0 && ret2 > 0) {
                    r.setCode(HttpResultEnum.CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            logger.error("员工申请修改批量保存异常" + e.getMessage());
            e.printStackTrace();
            r.setCode(HttpResultEnum.CODE_500.getCode());
            r.setMsg(HttpResultEnum.CODE_500.getMessage());
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        // 新增
        return r;
    }

    @Override
    public PageData getApplyEmpPageList(EmpApplyCo empApplyCo) {
        Page<EmpApplyCheckVo> page = new Page<EmpApplyCheckVo>(empApplyCo.getPage(), empApplyCo.getLimit());
        page.setRecords(this.baseMapper.getEmpApplyCheckList(empApplyCo, page));
        return new PageData(page.getTotal(),page.getRecords());
    }
}
