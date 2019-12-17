package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.EmployeeCo;
import com.settlement.entity.BaEmployee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.EmployeeVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
public interface BaEmployeeService extends IService<BaEmployee> {
    /** 未提交项目组员工记录 */
    PageData getNoSubmitEmployee(EmployeeCo employeeCo);
    /** 检查员工编号是否存在 */
    Result checkEmpCodeIsExist(String code);
    /** 新增员工 */
    Result insertProjectEmp(EmployeeVo employeeVo, String subStatus);
    /** 根据ID，删除员工 */
    Result deleteProjectEmp(Integer id);
    /** 根据ID，取得项目组员工信息 */
    BaEmployee getProjectEmpById(Integer id);
    /** 更新项目组员工 */
    Result updateProjectEmp(EmployeeVo employeeVo, String subStatus);
}
