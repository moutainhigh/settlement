package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCo;
import com.settlement.entity.BaApplyEmployee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.vo.EmployeeVo;

import java.util.List;

/**
 * <p>
 * 申请员工关联 服务类
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
public interface BaApplyEmployeeService extends IService<BaApplyEmployee> {
    /** 根据申请ID 查询员工信息 */
    PageData getApplyEmpPageList(EmpApplyCo empApplyCo);

}
