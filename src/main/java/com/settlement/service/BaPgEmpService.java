package com.settlement.service;

import com.settlement.entity.BaPgEmp;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

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

}
