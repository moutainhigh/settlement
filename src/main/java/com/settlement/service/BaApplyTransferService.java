package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ApplyTransferCo;
import com.settlement.entity.BaApplyTransfer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.ApplyTransferCheckVo;

/**
 * <p>
 * 申请移交项目和客户 服务类
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
public interface BaApplyTransferService extends IService<BaApplyTransfer> {
    /**移交审核列表**/
    PageData listPage(ApplyTransferCo applyTransferCo);
    /**移交审核详细**/
    PageData detailListPage(ApplyTransferCo applyTransferCo);
    /**审核移交操作**/
    Result checkApply(ApplyTransferCheckVo applyTransferCheckVo);
    /**检查是否存在审核移交未审核的数据**/
    Result checkApplyStatus(Integer[] ids);

}
