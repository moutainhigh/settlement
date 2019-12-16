package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCheckCo;
import com.settlement.entity.BaProjectGroupCheck;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupCheckVo;

/**
 * <p>
 * 项目审核表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
public interface BaProjectGroupCheckService extends IService<BaProjectGroupCheck> {
    /** 项目组审核列表 */
    PageData getPgCheckPageList(ProjectGroupCheckCo projectGroupCheckCo);
    /** 根据ID查询项目组审核记录 */
    ProjectGroupCheckVo getPgCheckById(Integer id);
    /** 项目组审核： 提交审核*/
    Result updatePgCheck(ProjectGroupCheckVo projectGroupCheckVo);
}
