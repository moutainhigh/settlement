package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.FormulaParamCo;
import com.settlement.entity.BaFormulaParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.FormulaParamVo;

/**
 * <p>
 * 结算公式参数表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
public interface BaFormulaParamService extends IService<BaFormulaParam> {
    /**加载列表**/
    PageData listPageData(FormulaParamCo formulaParamCo);
    /**添加 **/
    Result addFormulaParam(BaFormulaParam baFormulaParam);
    /**修改**/
    Result updateFormulaParam(BaFormulaParam baFormulaParam);
    /**删除 **/
    Result deleteFormulaParam(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**是否存在**/
    Result isExist(String code);
}
