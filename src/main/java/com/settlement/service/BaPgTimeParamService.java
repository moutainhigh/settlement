package com.settlement.service;

import com.settlement.entity.BaPgTimeParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

/**
 * <p>
 * 项目组结算时间点参数表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-16
 */
public interface BaPgTimeParamService extends IService<BaPgTimeParam> {
    /**时间点树勾选状态**/
    Result getCheckedValueByTimeParamId(Integer timeParamId);


}
