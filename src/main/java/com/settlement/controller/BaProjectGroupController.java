package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCo;
import com.settlement.co.UserCo;
import com.settlement.entity.BaProjectGroup;
import com.settlement.entity.SysUser;
import com.settlement.service.BaProjectGroupAssistantService;
import com.settlement.service.BaProjectGroupService;
import com.settlement.service.BaProjectGroupSettlementService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;

/**
 * <p>
 * 项目表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-20
 */
@RestController
public class BaProjectGroupController {

    @Autowired
    private BaProjectGroupService baProjectGroupService;
    @Autowired
    private BaProjectGroupAssistantService baProjectGroupAssistantService;
    @Autowired
    private BaProjectGroupSettlementService baProjectGroupSettlementService;

    /**
     * @description 项目组列表
     *
     * @auth admin
     * @date  2019-11-20
     * @param projectGroupCo    项目组查询条件
     * @param
     * @return
     */
    @GetMapping("/ba-project-group/pagedata")
    public PageData getProjectGroupList1(ProjectGroupCo projectGroupCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        projectGroupCo.setCurrentUserId(user.getId());
        return this.baProjectGroupService.getProjectGroupList(projectGroupCo);
    }

    /**
     * @descciption 根据CODE检查项目组是否存在
     *
     * @auth admin
     * @param code
     * @date 2019-11-21
     * @return
     */
    @GetMapping("/ba-project-group/isexist/{code}")
    public Result checkProjectGroupIsExist(@PathVariable String code) {
        return this.baProjectGroupService.checkProjectGroupIsExist(code);
    }

    /**
     * @description 项目组保存
     *
     * @auth admin
     * @date 2019-11-22
     * @return
     */
    @PostMapping("/ba-project-group/add")
    public Result addProjectGroup(ProjectGroupVo projectGroupVo) {
        // 新增项目组信息
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        projectGroupVo.setCreateTime(new Date());
        projectGroupVo.setEnabled(Const.ENABLED_N);
        projectGroupVo.setCreateUserId(user.getId());
        projectGroupVo.setDelFlag(Const.DEL_FLAG_N);
        projectGroupVo.setOwnerUserId(user.getId());
        return this.baProjectGroupService.saveProjectGroup(projectGroupVo);
    }

    /**
     * @description 项目组提交，并审核
     *
     * @auth admin
     * @date 2019-11-22
     * @return
     */
    @PostMapping("/ba-project-group/addsubmit")
    public Result addAndSubmitProjectGroup(ProjectGroupVo projectGroupVo) {
        // 新增项目组信息
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        projectGroupVo.setCreateTime(new Date());
        projectGroupVo.setEnabled(Const.ENABLED_N);
        projectGroupVo.setCreateUserId(user.getId());
        projectGroupVo.setDelFlag(Const.DEL_FLAG_N);
        projectGroupVo.setOwnerUserId(user.getId());
        projectGroupVo.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
        // 新增项目组审核信息
        return this.baProjectGroupService.addProjectGroup(projectGroupVo);
    }

    /**
     * @description 项目组编辑
     *
     * @auth admin
     * @date 2019-11-25
     * @param projectGroupVo
     * @return
     */
    @PutMapping("/ba-project-group/edit")
    public Result editProjectGroup(ProjectGroupVo projectGroupVo) {
        return this.baProjectGroupService.updateProjectGroup(projectGroupVo);
    }

    /**
     * @description 项目组编辑，并审核
     *
     * @auth admin
     * @date 2019-11-25
     * @param projectGroupVo
     * @return
     */
    @PutMapping("/ba-project-group/editsubmit")
    public Result editAndSubmitProjectGroup(ProjectGroupVo projectGroupVo) {
        return this.baProjectGroupService.updateAndSubmitProjectGroup(projectGroupVo);
    }

    /**
     * @description 项目组删除
     *
     * @auth admin
     * @date 2019-11-26
     * @param id
     * @return
     */
    @DeleteMapping("/ba-project-group/del/{id}")
    public Result delete(@PathVariable  Integer id) {
        return this.baProjectGroupService.deleteProjectGroup(id);
    }

    /**
     * @description 关联助理
     *
     * @auth admin
     * @date 2019-11-26
     * @param projectGroupVo
     * @return
     */
    @PostMapping("/ba-project-group/relate-assistant")
    public Result relateAssistant(ProjectGroupVo projectGroupVo) {
        return this.baProjectGroupAssistantService.batchInsert(projectGroupVo.getId(), projectGroupVo.getAssistants());
    }

    /**
     * @description 关联结算负责人
     *
     * @auth admin
     * @date 2019-11-27
     * @param projectGroupVo
     * @return
     */
    @PostMapping("/ba-project-group/relate-settlement")
    public Result relateSettlement(ProjectGroupVo projectGroupVo) {
        return this.baProjectGroupSettlementService.batchInsert(projectGroupVo.getId(), projectGroupVo.getSettlements());
    }

    /**
     * @description 根据客户id获得项目组列表
     * @auth kun
     * @param customerId
     * @return
     */
    @GetMapping("/ba-project-group/groups/{customerId}")
    public Result getGroupsByCustomerId(@PathVariable(value="customerId") Integer customerId) {
        return this.baProjectGroupService.getGroupsByCustomerId(customerId);
    }

    /**
     * @description 项目组启用检查，是否关联助理、结算负责人
     *
     * @auth admin
     * @date 2019-12-19
     * @param id
     * @return
     */
    @GetMapping("/ba-project-group/start-check/{id}")
    public Result pgStartCheck(@PathVariable(value="id") Integer id) {
        return this.baProjectGroupService.checkPgStart(id);
    }

    /**
     * @description 项目组启用
     *
     * @auth admin
     * @date 2019-12-20
     * @param id
     * @return
     */
    @PutMapping("/ba-project-group/start/{id}")
    public Result pgStart(@PathVariable(value="id") Integer id) {
        return this.baProjectGroupService.updatePgStart(id);
    }

    /**
     * @description 项目组停用
     *
     * @auth admin
     * @date 2020-1-3
     * @param id
     * @return
     */
    @PutMapping("/ba-project-group/stop/{id}")
    public Result pgStop(@PathVariable(value="id") Integer id) {
        return this.baProjectGroupService.updatePgStopById(id);
    }
}
