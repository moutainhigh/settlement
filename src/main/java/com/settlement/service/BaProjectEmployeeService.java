package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.entity.BaProjectEmployee;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
