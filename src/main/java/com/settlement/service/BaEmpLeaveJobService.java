package com.settlement.service;

import com.settlement.entity.BaEmpLeaveJob;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeaveJobVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-30
 */
public interface BaEmpLeaveJobService extends IService<BaEmpLeaveJob> {
    /** 员工离职：提交 */
    Result saveEmpLeaveJob(EmpLeaveJobVo empLeaveJobVo);
    /** 根据申请员工ID，查询员工离职信息 */
  //  BaEmpLeaveJob getEmpLeaveJobByApplyEmpId(Integer applyEmpId);



}
