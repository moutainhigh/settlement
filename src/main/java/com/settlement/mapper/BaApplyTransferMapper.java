package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.ApplyTransferCo;
import com.settlement.entity.BaApplyTransfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.BaApplyTransferVo;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 申请移交项目和客户 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
@Repository
public interface BaApplyTransferMapper extends BaseMapper<BaApplyTransfer> {
    /**移交审核列表**/
    List<BaApplyTransferVo> listPage(ApplyTransferCo applyTransferCo, Page<BaApplyTransferVo> page);
    /**移交审核-客户详情**/
    List<BaApplyTransferVo> transferCustomerDetails(BaApplyTransfer baApplyTransfer, Page<BaApplyTransferVo> page);
    /**移交审核-项目详情**/
    List<BaApplyTransferVo> transferProjectDetails(BaApplyTransfer baApplyTransfer, Page<BaApplyTransferVo> page);
}
