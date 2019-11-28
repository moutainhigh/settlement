package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.FormulaParamCo;
import com.settlement.entity.BaFormulaParam;
import com.settlement.service.BaFormulaParamService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * <p>
 * 结算公式参数表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/ba-formula-param")
public class BaFormulaParamController {

    @Autowired
    private BaFormulaParamService baFormulaParamService;

    /**
     * 列表页
     * @param formulaParamCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(FormulaParamCo formulaParamCo){
        PageData pageData = baFormulaParamService.listPageData(formulaParamCo);
        return  pageData;
    }

    /**
     * 添加
     * @param baFormulaParam
     * @return
     */
    @PostMapping("/add")
    public Result addFormulaParam(BaFormulaParam baFormulaParam){
        Result r = baFormulaParamService.addFormulaParam(baFormulaParam);
        return r;
    }

    /**
     * 修改
     * @param baFormulaParam
     * @return
     */
    @PutMapping("/update")
    public Result updateFormulaParam(BaFormulaParam baFormulaParam){
        Result r = baFormulaParamService.updateFormulaParam(baFormulaParam);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteFormulaParam(@PathVariable Integer id){
        Result r = baFormulaParamService.deleteFormulaParam(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baFormulaParamService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baFormulaParamService.updateEnableStop(id);
    }

    /**
     * 检查是否存在param_code
     * @param code
     * @return
     */
    @GetMapping("/isexist/{paramCode}")
    public Result isExist(@PathVariable(value="paramCode") String code) {
        return  baFormulaParamService.isExist(code);
    }
}
