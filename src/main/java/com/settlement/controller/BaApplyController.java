package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.ApplyCo;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaApply;
import com.settlement.entity.SysUser;
import com.settlement.service.BaApplyService;
import com.settlement.utils.Result;
import com.settlement.vo.ApplyWorkAttendanceVo;
import com.settlement.vo.BaApplyVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 * 申请修改员工信息表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-12-04
 */
@RestController
@RequestMapping("/ba-apply")
public class BaApplyController {

    @Autowired
    private BaApplyService baApplyService;
    /**
     *
     * @param applyCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(ApplyCo applyCo){
        PageData pageData = baApplyService.listPageData(applyCo);
        return  pageData;
    }

    /**
     * 加载考勤审核列表
     * @param applyCo
     * @return
     */
    @GetMapping("/check/workattend/pagedata")
    public PageData listCheckPageData(ApplyCo applyCo){
        PageData pageData = baApplyService.listPageData(applyCo);
        return  pageData;
    }


    /**
     * 审核考勤修改
     * @param baApplyVo
     * @return
     */
    @PostMapping("/check/workattend")
    public Result checkWorkattend(BaApplyVo baApplyVo){

        return baApplyService.checkWorkattend(baApplyVo);
    }
    /**
     *修改
     * @param baApplyVo
     * @return
     */
    @PutMapping("/update")
    public Result update(BaApplyVo baApplyVo){
        Result r = baApplyService.update(baApplyVo);
        return r;
    }

    /**
     * 添加考勤记录
     * @param baApplyVo
     * @return
     */
    @PostMapping("/add/attendance")
    public Result addAttendance(BaApplyVo baApplyVo, HttpSession session){
        SysUser user =(SysUser)session.getAttribute("user");
        baApplyVo.setApplyUser(user.getId());
        Result r = baApplyService.addAttendance(baApplyVo);
        return r;
    }

    /**
     *
     * @param baApplyVo
     * @return
     */
    @PostMapping("/add/employee")
    public Result addEmployee(BaApplyVo baApplyVo){
        Result r = baApplyService.addEmployee(baApplyVo);
        return r;
    }

    /**
     * 验证修改口令
     * @param baApply
     * @return
     */
    @PostMapping("/verify/passcode")
    public Result verifyPasscode(BaApply baApply){
        return baApplyService.verifyPasscode(baApply);
    }

    /**
     * 获得每个项目的申请修改的次数
     * @param projectId
     * @return
     */
    @GetMapping("/apply/{count}/{monthValue}")
    public Result getApplyCountByProjectId(@PathVariable Integer projectId,@PathVariable String monthValue){
        return baApplyService.getApplyCountByProjectId(projectId,monthValue);
    }
}
