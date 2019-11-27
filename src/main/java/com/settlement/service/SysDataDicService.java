package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.DataDicCo;
import com.settlement.entity.SysDataDic;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

/**
 * <p>
 * 数据字典表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-19
 */
public interface SysDataDicService extends IService<SysDataDic> {
    /**
     * 新增
     */
    public Result saveDataDic(SysDataDic sysDataDic);

    /**
     * 修改
     */
    Result updateDataDic(SysDataDic sysDataDic);

    /**
     * 删除：标志位更新为Y
     */
    Result deleteDataDic(Integer id);

    /**
     * 启用
     */
    Result updateDataDicStart(Integer id);

    /**
     * 停用信息
     */
    Result updateDataDicStop(Integer id);

    /**
     * pidSelect
     */
    public Result pidSelectData();

    /**
     *加载数据字典列表页
     */
    public PageData listPageData(DataDicCo dataDicCo);

    /**
     * 更新数据字典状态
     **/
    public Result updateStatus(Integer id,boolean enable);

    Result dicCodeIsExist(String dicCode);
}