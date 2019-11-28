package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.BaCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaCustomerVo;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
public interface BaCustomerService extends IService<BaCustomer> {
    /**加载列表**/
    PageData listPageData(CustomerCo customerCo);
    /**添加 **/
    Result add(BaCustomerVo baCustomerVo);
    /**修改**/
    Result update(BaCustomerVo baCustomerVo);
    /**删除 **/
    Result delete(Integer id);
    /**启用状态 **/
    Result updateEnableStart(Integer id);
    /**停用状态 **/
    Result updateEnableStop(Integer id);

}
