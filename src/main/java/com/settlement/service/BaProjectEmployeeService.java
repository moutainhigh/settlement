package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.entity.BaProjectEmployee;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectEmployeeVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
public interface BaProjectEmployeeService extends IService<BaProjectEmployee> {
    /** 未提交项目组员工记录 */
    PageData getNoSubmitEmployee(ProjectEmployeeCo projectEmployeeCo);
    /** 检查员工编号是否存在 */
    Result checkEmpCodeIsExist(String code);
    /** 新增员工 */
    Result insertProjectEmp(ProjectEmployeeVo projectEmployeeVo);

}
