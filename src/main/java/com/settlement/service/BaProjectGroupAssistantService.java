package com.settlement.service;

import com.settlement.entity.BaProjectGroupAssistant;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
public interface BaProjectGroupAssistantService extends IService<BaProjectGroupAssistant> {

    /** 批量新增项目组助理 */
    Result batchInsert(Integer pgId, String assistantIds);

}
