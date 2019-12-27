package com.settlement.controller;


import com.settlement.service.BaApplyAttendanceService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 申请考勤关联 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/ba-apply-attendance")
public class BaApplyAttendanceController {
    @Autowired
    private BaApplyAttendanceService baApplyAttendanceService;
    /**
     *  检查数据是否有修改中的数据,不能提交
     * @param ids
     * @return
     */
    @GetMapping("/check/apply/modify/{ids}")
    public Result checkApplyModify(@PathVariable Integer[] ids){
        return this.baApplyAttendanceService.checkApplyModify(ids);
    }
}
