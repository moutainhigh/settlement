package com.settlement.mapper;

import com.settlement.entity.BaTransfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface BaTransferMapper extends BaseMapper<BaTransfer> {
    /**移交批量插入**/
    Integer insertBatch(List<BaTransfer> baTransfers);
}
