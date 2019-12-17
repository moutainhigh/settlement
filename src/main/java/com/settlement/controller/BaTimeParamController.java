package com.settlement.controller;


import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaTimeParam;
import com.settlement.entity.SysUser;
import com.settlement.service.BaTimeParamService;
import com.settlement.utils.Result;
import com.settlement.bo.PageData;
import com.settlement.vo.BaTimeParamVo;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>
 * 时间点参数表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-27
 */
@RestController
@RequestMapping("/ba-time-param")
public class BaTimeParamController {
    @Autowired
    private BaTimeParamService baTimeParamService;

    /**
     * 列表页
     * @param timeParamCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(TimeParamCo timeParamCo){
        PageData pageData = baTimeParamService.listPageData(timeParamCo);
        return  pageData;
    }

    /**
     * 添加
     * @param baTimeParamVo
     * @return
     */
    @PostMapping("/add")
    public Result addTimeParam(BaTimeParamVo baTimeParamVo, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        baTimeParamVo.setCreateUserId(user.getId());
        Result r = baTimeParamService.addTimeParam(baTimeParamVo);
        return r;
    }

    /**
     * 修改
     * @param baTimeParamVo
     * @return
     */
    @PutMapping("/update")
    public Result updateTimeParam(BaTimeParamVo baTimeParamVo){
        Result r = baTimeParamService.updateTimeParam(baTimeParamVo);
        return r;
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteTimeParam(@PathVariable Integer id){
        Result r = baTimeParamService.deleteTimeParam(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baTimeParamService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baTimeParamService.updateEnableStop(id);
    }
    /**
     * 检查是否存在param_code
     * @param code
     * @return
     */
    @GetMapping("/isexist/{code}")
    public Result isExist(@PathVariable(value="code") String code) {
        return  baTimeParamService.isExist(code);
    }

    /**
     * 考勤时间点前
     * @return
     */
    @GetMapping("/judge/workattendance/stoptime/{projectId}")
    public Result judgeWorkattendanceStopTime(@PathVariable Integer projectId) {
        return baTimeParamService.judgeWorkattendanceStopTime(projectId);
    }
    /**
     * 考勤完成时间点前
     * @return
     */
    @GetMapping("/judge/workattendance/completetime/{projectId}")
    public Result judgeWorkattendanceCompleteTime(@PathVariable Integer projectId) {
        return baTimeParamService.judgeWorkattendanceCompleteTime(projectId);
    }
    /**
     * 考勤完成时间和考勤完成时间点之间
     * @return
     */
    @GetMapping("/judge/workattendance/date/{projectId}")
    public Result judgeWorkattendanceDate(@PathVariable Integer projectId) {
        return baTimeParamService.judgeWorkattendanceDate(projectId);
    }
}
