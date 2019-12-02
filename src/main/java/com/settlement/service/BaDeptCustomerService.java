package com.settlement.service;

import com.settlement.entity.BaDeptCustomer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
public interface BaDeptCustomerService extends IService<BaDeptCustomer> {
    /**添加 **/
    Result add(BaDeptCustomer baDeptCustomer);
    /**修改**/
    Result update(BaDeptCustomer baDeptCustomer);
    /**删除 **/
    Result delete(Integer id);
}
