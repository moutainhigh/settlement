package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ContractCo;
import com.settlement.entity.BaContract;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.BaContractVo;

/**
 * <p>
 * 合同管理表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
public interface BaContractService extends IService<BaContract> {
    /**加载列表**/
    PageData listPageData(ContractCo contractCo);
    /**添加 **/
    Result add(BaContractVo baContractVo);
    /**修改**/
    Result update(BaContractVo baContractVo);
    /**删除 **/
    Result delete(Integer id);
    /**根据id获得BaContractVo**/
    BaContractVo getBaContractVoById(Integer id);
    /**是否存在**/
    Result isExist(String contractNo);
}
