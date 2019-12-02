package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.service.BaProjectEmployeeService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
@RestController
public class BaProjectEmployeeController {
    @Autowired
    private BaProjectEmployeeService baProjectEmployeeService;

    /**
     * @description 未提交员工数据
     *
     * @auth admin
     * @date 2019-11-28
     * @param projectEmployeeCo
     * @return
     */
    @GetMapping("/ba-project-employee/pagedata")
    public PageData getNoSubmitPageData(ProjectEmployeeCo projectEmployeeCo) {
        return this.baProjectEmployeeService.getNoSubmitEmployee(projectEmployeeCo);
    }

}
