package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCo;
import com.settlement.service.BaEmpApplyCheckService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpApplyCheckVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.List;

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
     * @description 员工申请修改列表
     *
     * @auth admin
     * @date 2019-12-24
     * @param empApplyCo
     * @return
     */
    @GetMapping("/ba-emp-apply-check/pagedata")
    public PageData getApplyEmpPageList(EmpApplyCo empApplyCo) {
        return this.baEmpApplyCheckService.getApplyEmpPageList(empApplyCo);
    }

}
