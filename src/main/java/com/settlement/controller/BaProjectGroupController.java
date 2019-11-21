package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCo;
import com.settlement.co.UserCo;
import com.settlement.entity.BaProjectGroup;
import com.settlement.entity.SysUser;
import com.settlement.service.BaProjectGroupService;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * @description 项目组列表
     *
     * @auth admin
     * @date  2019-11-20
     * @param projectGroupCo    项目组查询条件
     * @param model
     * @return
     */
    @GetMapping("/ba-project-group/pagedata")
    public PageData getProjectGroupList1(ProjectGroupCo projectGroupCo, Model model) {
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


}
