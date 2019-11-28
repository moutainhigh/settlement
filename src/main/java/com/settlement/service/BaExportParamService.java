package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ExportParamCo;
import com.settlement.entity.BaExportParam;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.ExportParamVo;

/**
 * <p>
 * 导出参数表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-27
 */
public interface BaExportParamService extends IService<BaExportParam> {
    /**加载列表**/
    PageData listPageData(ExportParamCo exportParamCo);
    /**添加 **/
    Result addExportParam(BaExportParam baExportParam);
    /**修改**/
    Result updateExportParam(BaExportParam baExportParam);
    /**删除 **/
    Result deleteExportParam(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**是否存在**/
    Result isExist(String code);
}
