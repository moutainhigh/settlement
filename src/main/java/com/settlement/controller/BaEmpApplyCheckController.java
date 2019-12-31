package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCheckCo;
import com.settlement.entity.SysUser;
import com.settlement.service.BaEmpApplyCheckService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpApplyCheckVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@RestController
public class BaEmpApplyCheckController {
    @Autowired
    private BaEmpApplyCheckService baEmpApplyCheckService;

    /**
     * @description 员工申请修改提交
     *
     * @param empApplyCheckVo
     * @param empIds
     * @return
     */
    @PostMapping("/ba-emp-apply-check/apply")
    public Result empApply(EmpApplyCheckVo empApplyCheckVo, @RequestParam(value="empIds") String empIds) {
        return baEmpApplyCheckService.insertEmpApply(empApplyCheckVo, empIds);
    }

    /**
     * @description 员工申请修改列表-审核页面
     *
     * @auth admin
     * @date 2019-12-24
     * @param empApplyCheckCo
     * @return
     */
    @GetMapping("/ba-emp-apply-check/check-pagedata")
    public PageData getCheckEmpPageList(EmpApplyCheckCo empApplyCheckCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        empApplyCheckCo.setCheckUserId(user.getId());
        return this.baEmpApplyCheckService.getApplyEmpPageList(empApplyCheckCo);
    }

    /**
     * @description 员工申请修改列表-申请修改记录页面
     *
     * @auth admin
     * @date 2019-12-27
     * @param empApplyCheckCo
     * @return
     */
    @GetMapping("/ba-emp-apply-check/apply-pagedata")
    public PageData getApplyEmpPageList(EmpApplyCheckCo empApplyCheckCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        empApplyCheckCo.setApplyUserId(user.getId());
        return this.baEmpApplyCheckService.getApplyEmpPageList(empApplyCheckCo);
    }

    /**
     * @description 审核提交
     *
     * @auth admin
     * @date 2019-12-27
     * @param empApplyCheckVo
     * @return
     */
    @PutMapping("/ba-emp-apply-check")
    public Result checkEmpApply(EmpApplyCheckVo empApplyCheckVo) {
        return this.baEmpApplyCheckService.updateCheckResult(empApplyCheckVo);
    }

    /**
     * @description 验证口令
     *
     * @auth admin
     * @date 2019-12-27
     * @param empApplyCheckVo
     * @return
     */
    @PostMapping("/ba-emp-apply-check/verify")
    public Result verifyUpdatePassword(EmpApplyCheckVo empApplyCheckVo) {
        return this.baEmpApplyCheckService.verifyUpdatePassword(empApplyCheckVo);
    }

}
