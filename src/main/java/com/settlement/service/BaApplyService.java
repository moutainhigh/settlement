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
    Result updateCheck(BaApplyVo baApplyVo);
    /**申请修改考勤记录**/
    Result addAttendance(BaApplyVo baApplyVo);
    /**申请修改员工记录**/
    Result addEmployee(BaApplyVo baApplyVo);
    /**验证口令**/
    Result verifyPasscode(BaApply baApply);
    /**获得每个项目的申请修改的次数**/
    Result getApplyCountByProjectId(Integer projectId,String monthValue);
    /**根据id 获得BaApplyVo**/
    BaApplyVo getApplyVoById(Integer id);
    /**审核考勤修改**/
    Result checkWorkattend(BaApplyVo baApplyVo);
    /**加载考勤审核列表**/
    PageData listCheckWorkAttendancePageData(ApplyCo applyCo);
    /**申请修改考勤--申请修改列表考勤信息列表 ASSISTANT**/
    PageData listApplyWorkAttendancelistPageData(ApplyCo applyCo);
    /**申请修改考勤--审核详细列表 AM **/
    PageData listApplyCheckWorkAttendancelistPageData(ApplyCo applyCo);
    /**提交修改的考勤记录**/
    Result commitWorkAttendance(Integer[] ids,String applyId);
    /**检查每一条申请记录中全部是否还有未修改完的数据**/
    Result checkApplyStatus(Integer applyId);
}
