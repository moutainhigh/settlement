package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCheckCo;
import com.settlement.entity.BaEmpApplyCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpApplyCheckVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
public interface BaEmpApplyCheckService extends IService<BaEmpApplyCheck> {
    /** 保存员工申请修改 */
    Result insertEmpApply(EmpApplyCheckVo empApplyCheckVo, String empIds);
    /** 获取员工申请审核列表 */
    PageData getApplyEmpPageList(EmpApplyCheckCo empApplyCheckCo);
    /** 更新审核结果 */
    Result updateCheckResult(EmpApplyCheckVo empApplyCheckVo);
    /** 验证口令 */
    Result verifyUpdatePassword(EmpApplyCheckVo empApplyCheckVo);
}
