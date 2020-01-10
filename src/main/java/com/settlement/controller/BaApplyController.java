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
import org.apache.shiro.SecurityUtils;
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
     * 修改通过的考勤信息列表 AM
     * @param applyCo
     * @return
     */
    @GetMapping("/check/detail/workattendancelist/pagedata")
    public PageData listApplyCheckDetailWorkAttendancelistPageData(ApplyCo applyCo){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        applyCo.setApplyUser(user.getId());
        PageData pageData = baApplyService.listApplyCheckWorkAttendancelistPageData(applyCo);
        return  pageData;
    }

    /**
     * 修改通过的考勤信息列表 Assistant
     * @param applyCo
     * @return
     */
    @GetMapping("/apply/workattendancelist/pagedata")
    public PageData listApplyWorkAttendancelistPageData(ApplyCo applyCo){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        applyCo.setApplyUser(user.getId());
        PageData pageData = baApplyService.listApplyWorkAttendancelistPageData(applyCo);
        return  pageData;
    }
    /**
     * 加载考勤审核列表
     * @param applyCo
     * @return
     */
    @GetMapping("/check/workattend/pagedata")
    public PageData listCheckPageData(ApplyCo applyCo){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();

        applyCo.setCheckUser(user.getId());
        PageData pageData = baApplyService.listCheckWorkAttendancePageData(applyCo);
        return  pageData;
    }

    /**
     * 提交修改的考勤记录
     * @param ids
     * @return
     */
    @PutMapping("/work-attendance/commit/{ids}/{applyId}")
    public Result commitWorkAttendance(@PathVariable Integer[] ids,@PathVariable String applyId) {
        return baApplyService.commitWorkAttendance(ids,applyId);
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
    @PutMapping("/update/check")
    public Result updateCheck(BaApplyVo baApplyVo){
        Result r = baApplyService.updateCheck(baApplyVo);
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

    /**
     * 检查每一条申请记录中全部是否还有未修改完的数据
     * @param applyId
     * @return
     */
    @GetMapping("/check/applystatus/{applyId}")
    public Result checkApplyStatus(@PathVariable  Integer applyId) {
        return baApplyService.checkApplyStatus(applyId);
    }
}
