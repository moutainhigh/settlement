package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.CityCo;
import com.settlement.entity.BaCity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
public interface BaCityService extends IService<BaCity> {
    /**加载列表**/
    PageData listPageData(CityCo cityCo);
    /**添加 **/
    Result add(BaCity baCity);
    /**修改**/
    Result update(BaCity baCity);
    /**删除 **/
    Result delete(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);
    /**查询BaCity 列表**/
    Result getBaCityListAjax();
    /**查询BaCity 列表**/
    List<BaCity> getBaCityList();
}
