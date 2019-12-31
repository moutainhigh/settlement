package com.settlement.service;

import com.settlement.entity.BaPgEmp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import io.swagger.models.auth.In;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-12-11
 */
public interface BaPgEmpService extends IService<BaPgEmp> {
    /** 项目组员工提交 */
    Result updateEmpSubByBatchId(String ids,Integer pgId);
    /** 根据项目组ID，员工ID，员工入场状态取得项目组员工 */
    BaPgEmp getBaPgEmpByPgIdAndEmpId(Integer pgId, Integer empId, String entranceStatus);

}
