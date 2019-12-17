package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCo;
import com.settlement.entity.BaProjectGroup;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupVo;

import java.util.List;

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
    /** 项目组新增 */
    Result saveProjectGroup(ProjectGroupVo projectGroupVo);
    /** 项目组新增，并审核 */
    Result addProjectGroup(ProjectGroupVo projectGroupVo);
    /** 项目组编辑*/
    Result updateProjectGroup(ProjectGroupVo projectGroupVo);
    /** 项目组编辑，并审核 */
    Result updateAndSubmitProjectGroup(ProjectGroupVo projectGroupVo);
    /** 项目组删除 */
    Result deleteProjectGroup(Integer id);
    /** 根据ID查询项目组，助理 */
    ProjectGroupVo getProjectGroupAssistantById(Integer id);
    /** 根据ID查询项目组，结算负责人 */
    ProjectGroupVo getProjectGroupSettlementById(Integer id);
    /** 根据customerId获得项目组**/
    Result getGroupsByCustomerId(Integer customerId);
}
