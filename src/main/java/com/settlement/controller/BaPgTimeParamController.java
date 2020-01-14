package com.settlement.controller;


import com.settlement.service.BaPgTimeParamService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 项目组结算时间点参数表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-12-16
 */
@RestController
@RequestMapping("/ba-pg-time-param")
public class BaPgTimeParamController {
    @Autowired
    private BaPgTimeParamService baPgTimeParamService;
    @GetMapping("/checked/{timeParamId}")
    public Result getCheckedValueByTimeParamId(@PathVariable Integer timeParamId){
        return  baPgTimeParamService.getCheckedValueByTimeParamId(timeParamId);
    }
    /**
     * 检查有没有设备时间点
     * @param projectId
     * @return
     */
    @GetMapping("/check/time/status/{projectId}")
    public Result checkTimeStatus(@PathVariable String projectId) {
        return  baPgTimeParamService.checkTimeStatus(projectId);
    }
}
