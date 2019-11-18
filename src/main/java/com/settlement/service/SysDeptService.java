package com.settlement.service;

import com.settlement.entity.SysDept;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

/**
 * <p>
 * 部门表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
public interface SysDeptService extends IService<SysDept> {

    Result getDeptSelect();

}
