package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaWorkAttendance;
import com.settlement.service.BaWorkAttendanceService;
import com.settlement.utils.Result;
import com.settlement.vo.BaWorkAttendanceVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
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
     * 考勤申请修改页面数据
     * @param workAttendanceCo
     * @return
     */
    @GetMapping("/apply/pagedata")
    public PageData listApplyPageData(WorkAttendanceCo workAttendanceCo){
        PageData pageData = baWorkAttendanceService.listApplyPageData(workAttendanceCo);
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
     * 生成考勤记录
     * @param ids
     * @param pgId
     * @return
     */
    @PostMapping("/generate")
    public Result generateWorkAttendance(String ids, Integer pgId) {
        return  baWorkAttendanceService.generateWorkAttendance(ids,pgId);
    }

    /**
     * 检查数据是否有修改中的数据,不能提交
     * @param ids
     * @return
     */
    @GetMapping("/check/status/{ids}")
    public Result checkStatus(@PathVariable Integer[] ids){
        return baWorkAttendanceService.checkStatus(ids);
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
     *　考勤修改
     * @param baWorkAttendance
     * @return
     */
    @PostMapping("/update")
    public Result update(BaWorkAttendance baWorkAttendance){
        Result r = baWorkAttendanceService.update(baWorkAttendance);
        return r;
    }

    /**
     * 考勤提交
     * @param ids
     * @return
     */
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
