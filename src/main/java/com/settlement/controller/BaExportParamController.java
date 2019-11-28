package com.settlement.controller;

import com.settlement.co.ExportParamCo;
import com.settlement.entity.BaExportParam;
import com.settlement.service.BaExportParamService;
import com.settlement.vo.ExportParamVo;
import com.settlement.utils.Result;
import com.settlement.bo.PageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 导出参数表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-27
 */
@RestController
@RequestMapping("/ba-export-param")
public class BaExportParamController {

    @Autowired
    private BaExportParamService baExportParamService;

    /**
     *
     * @param exportParamCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(ExportParamCo exportParamCo){
        PageData pageData = baExportParamService.listPageData(exportParamCo);
        return  pageData;
    }

    /**
     * 添加
     * @param baExportParam
     * @return
     */
    @PostMapping("/add")
    public Result addExportParam(BaExportParam baExportParam){
        Result r = baExportParamService.addExportParam(baExportParam);
        return r;
    }

    /**
     *修改
     * @param baExportParam
     * @return
     */
    @PutMapping("/update")
    public Result updateExportParam(BaExportParam baExportParam){
        Result r = baExportParamService.updateExportParam(baExportParam);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteExportParam(@PathVariable Integer id){
        Result r = baExportParamService.deleteExportParam(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baExportParamService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baExportParamService.updateEnableStop(id);
    }

    /**
     * 检查是否存在
     * @param code
     * @return
     */
    @GetMapping("/isexist/{code}")
    public Result isExist(@PathVariable(value="code") String code) {
        return  baExportParamService.isExist(code);
    }

}
