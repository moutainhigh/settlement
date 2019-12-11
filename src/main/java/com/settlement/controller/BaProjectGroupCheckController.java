package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.ProjectGroupCheckCo;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/ba-project-group-check/pagedata")
    public PageData getPgCheckPage(ProjectGroupCheckCo projectGroupCheckCo) {
        return null;
    }

}
