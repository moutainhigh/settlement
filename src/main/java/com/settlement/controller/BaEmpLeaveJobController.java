package com.settlement.controller;


import com.settlement.entity.BaEmpLeaveJob;
import com.settlement.service.BaEmpLeaveJobService;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeaveJobVo;
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
 * @author kun
 * @since 2019-12-30
 */
@RestController
@RequestMapping("/ba-emp-leave-job")
public class BaEmpLeaveJobController {
    @Autowired
    private BaEmpLeaveJobService baEmpLeaveJobService;

    /**
     * 员工离职：保存
     *
     * @auth admin
     * @date 2019-12-30
     * @param empLeaveJobVo
     * @return
     */
    @PostMapping("save")
    public Result save(EmpLeaveJobVo empLeaveJobVo) {
        return this.baEmpLeaveJobService.saveEmpLeaveJob(empLeaveJobVo);
    }

}
