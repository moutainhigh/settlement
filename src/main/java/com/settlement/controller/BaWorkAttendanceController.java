package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaWorkAttendance;
import com.settlement.service.BaWorkAttendanceService;
import com.settlement.utils.Result;
import com.settlement.vo.BaWorkAttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 考勤表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/ba-work-attendance")
public class BaWorkAttendanceController {

    @Autowired
    private BaWorkAttendanceService baWorkAttendanceService;
    /**
     * 加载考勤管理页面数据
     * @param workAttendanceCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(WorkAttendanceCo workAttendanceCo){
        PageData pageData = baWorkAttendanceService.listPageData(workAttendanceCo);
        return  pageData;
    }

    /**
     * 考勤申请修改-审核通过-修改考勤页面
     * @param workAttendanceCo
     * @return
     */
    @GetMapping("/apply/workattend/pagedata")
    public PageData listApplyAttendPageData(WorkAttendanceCo workAttendanceCo) {
        PageData pageData = baWorkAttendanceService.getWorkAttendanceByApplyId(workAttendanceCo);
        return  pageData;
    }

    /**
     * 添加
     * @param baWorkAttendanceVo
     * @return
     */
    @PostMapping("/add")
    public Result add(BaWorkAttendanceVo baWorkAttendanceVo){
        Result r = baWorkAttendanceService.add(baWorkAttendanceVo);
        return r;
    }

    /**
     *修改
     * @param baWorkAttendance
     * @return
     */
    @PostMapping("/update")
    public Result update(BaWorkAttendance baWorkAttendance){
        Result r = baWorkAttendanceService.update(baWorkAttendance);
        return r;
    }

    @PutMapping("/commit/{ids}")
    public Result commitWorkAttendance(@PathVariable String ids){
        String[] ids2=ids.split(",");
        Result r = baWorkAttendanceService.commitWorkAttendance(ids2);
        return r;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public BaWorkAttendance getBaWorkAttendanceVoById(@PathVariable Integer id){
        return this.baWorkAttendanceService.getBaWorkAttendanceVoById(id);
    }
}
