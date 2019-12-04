package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.entity.BaApplyAttendance;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaCustomerVo;

/**
 * <p>
 * 申请考勤关联 服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
public interface BaApplyAttendanceService extends IService<BaApplyAttendance> {
    /**添加申请修改记录**/
    Result add(BaApplyAttendance baApplyAttendance);
}
