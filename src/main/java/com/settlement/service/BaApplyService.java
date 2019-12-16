package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ApplyCo;
import com.settlement.entity.BaApply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaApplyVo;
import com.settlement.vo.CheckStatusVo;

import java.util.List;

/**
 * <p>
 * 申请修改员工信息表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-04
 */
public interface BaApplyService extends IService<BaApply> {

    /**加载列表**/
    PageData listPageData(ApplyCo applyCo);
    /**修改**/
    Result update(BaApplyVo baApplyVo);
    /**申请修改考勤记录**/
    Result addAttendance(BaApplyVo baApplyVo);
    /**申请修改员工记录**/
    Result addEmployee(BaApplyVo baApplyVo);
    /**验证口令**/
    Result verifyPasscode(BaApply baApply);

}
