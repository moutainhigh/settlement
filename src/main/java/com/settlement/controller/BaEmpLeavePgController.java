package com.settlement.controller;


import com.settlement.entity.BaEmpLeavePg;
import com.settlement.service.BaEmpLeavePgService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeavePgVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  员工离场前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-12-29
 */
@RestController
@RequestMapping("/ba-emp-leave-pg")
public class BaEmpLeavePgController {

    @Autowired
    private BaEmpLeavePgService baEmpLeavePgService;

    /**
     * @description 员工离场：保存
     *
     * @auth admin
     * @date 2019-12-30
     * @param empLeavePgVo
     * @return
     */
    @PostMapping("/save")
    public Result save(EmpLeavePgVo empLeavePgVo) {
        return baEmpLeavePgService.saveEmpLeavePg(empLeavePgVo);
    }

    /**
     * @description 员工离场：提交
     *
     * @auth admin
     * @date 2019-12-30
     * @param empLeavePgVo
     * @return
     */
    @PutMapping
    public Result submit(EmpLeavePgVo empLeavePgVo) {
        return  this.baEmpLeavePgService.submitEmpLeavePg(empLeavePgVo);
    }

}
