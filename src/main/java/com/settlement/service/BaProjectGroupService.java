package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCo;
import com.settlement.entity.BaProjectGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-20
 */
public interface BaProjectGroupService extends IService<BaProjectGroup> {
    /** 项目组列表 */
    PageData getProjectGroupList(ProjectGroupCo baProjectGroupCo);
    /** 根据CODE检查项目组是否存在 */
    Result checkProjectGroupIsExist(String code);

}
