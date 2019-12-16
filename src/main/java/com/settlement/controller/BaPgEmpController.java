package com.settlement.controller;


import com.settlement.service.BaPgEmpService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-12-11
 */
@RestController
public class BaPgEmpController {
    @Autowired
    private BaPgEmpService baPgEmpService;

    /**
     * @description 提交
     *
     * @auth admin
     * @date 2019-12-7
     * @param ids
     * @return
     */
    @PostMapping("/ba-pg-employee/sub")
    public Result submitProjectEmp(String ids, Integer pgId) {
        return this.baPgEmpService.updateEmpSubByBatchId(ids, pgId);
    }

}
