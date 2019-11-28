package com.settlement.controller;


import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaTimeParam;
import com.settlement.service.BaTimeParamService;
import com.settlement.utils.Result;
import com.settlement.bo.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 时间点参数表 前端控制器
 * </p>
 *
 * @author admin
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
     * @param baTimeParam
     * @return
     */
    @PostMapping("/add")
    public Result addTimeParam(BaTimeParam baTimeParam){
        Result r = baTimeParamService.addTimeParam(baTimeParam);
        return r;
    }

    /**
     * 修改
     * @param baTimeParam
     * @return
     */
    @PutMapping("/update")
    public Result updateTimeParam(BaTimeParam baTimeParam){
        Result r = baTimeParamService.updateTimeParam(baTimeParam);
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

}
