package com.settlement.service;

import com.settlement.entity.BaEmpLeavePg;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeavePgVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-29
 */
public interface BaEmpLeavePgService extends IService<BaEmpLeavePg> {
    /** 员工离场：保存 */
    Result saveEmpLeavePg(EmpLeavePgVo empLeavePgVo);
    /** 员工离场： 提交 */
   // Result submitEmpLeavePg(EmpLeavePgVo empLeavePgVo);
    /** 根据申请员工ID ，查询员工离场记录 */
  /*  BaEmpLeavePg getEmpLeavePgByApplyEmpId(Integer applyEmpId);*/
}
