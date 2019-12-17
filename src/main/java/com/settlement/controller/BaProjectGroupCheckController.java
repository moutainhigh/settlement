package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCheckCo;
import com.settlement.entity.SysUser;
import com.settlement.service.BaProjectGroupCheckService;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectGroupCheckVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 项目审核表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-22
 */
@RestController
public class BaProjectGroupCheckController {
    @Autowired
    private BaProjectGroupCheckService baProjectGroupCheckService;

    /**
     * @description 项目组审核列表
     *
     * @auth admin
     * @date 2019-12-12
     * @param projectGroupCheckCo
     * @return
     */
    @GetMapping("/ba-project-group-check/pagedata")
    public PageData getPgCheckPage(ProjectGroupCheckCo projectGroupCheckCo) {
        SysUser currentUser = (SysUser) SecurityUtils.getSubject().getPrincipal();
        projectGroupCheckCo.setCurrentUserId(currentUser.getId());
        return this.baProjectGroupCheckService.getPgCheckPageList(projectGroupCheckCo);
    }

    /**
     * @description 项目组审核：提交审核
     *
     * @param projectGroupCheckVo
     * @return
     */
    @PutMapping("/ba-project-group-check")
    public Result pgCheck(ProjectGroupCheckVo projectGroupCheckVo) {
        return this.baProjectGroupCheckService.updatePgCheck(projectGroupCheckVo);
    }

}
