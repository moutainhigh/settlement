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
     *
     * @param workAttendanceCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(WorkAttendanceCo workAttendanceCo){
        PageData pageData = baWorkAttendanceService.listPageData(workAttendanceCo);
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
     * @param baWorkAttendanceVo
     * @return
     */
    @PutMapping("/update")
    public Result update(BaWorkAttendanceVo baWorkAttendanceVo){
        Result r = baWorkAttendanceService.update(baWorkAttendanceVo);
        return r;
    }

    /**
     * 根据id查询
     * @param id
     * @return
     */
    @PutMapping("/{id}")
    public BaWorkAttendance getBaWorkAttendanceVoById(Integer id){
        return this.baWorkAttendanceService.getBaWorkAttendanceVoById(id);
    }
}
