package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCo;
import com.settlement.entity.BaApplyEmployee;
import com.settlement.service.BaApplyEmployeeService;
import io.swagger.annotations.OAuth2Definition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 申请员工关联 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@RestController
public class BaApplyEmployeeController {

    @Autowired
    private BaApplyEmployeeService baApplyEmployeeService;

    /**
     * @description 申请员工 详细
     *
     * @auth admin
     * @date 2019-12-26
     * @param empApplyCo
     * @return
     */
    @GetMapping("/ba-apply-employee/pagedata")
    public PageData getApplyEmpPageList(EmpApplyCo empApplyCo) {
        return this.baApplyEmployeeService.getApplyEmpPageList(empApplyCo);
    }

}
