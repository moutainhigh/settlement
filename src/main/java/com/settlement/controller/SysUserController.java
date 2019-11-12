package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.UserCo;
import com.settlement.entity.SysUser;
import com.settlement.service.SysUserService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Controller
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @ResponseBody
    @GetMapping("/sys-user/pagedata")
    public PageData getUserList(UserCo userCo, Model model) {
        IPage<SysUser> page = new Page<SysUser>(userCo.getPage(), userCo.getLimit());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.like(StringUtils.isNotBlank(userCo.getKeyword()),"real_name",userCo.getKeyword());
        queryWrapper.orderByDesc("create_time");
        this.sysUserService.page(page, queryWrapper);
        return new PageData(page.getTotal(), page.getRecords());
    }

    @GetMapping("/sys-user/toadd")
    public String toUserAdd() {
        // 获取页面下拉框数据
        return "user/user-add";
    }

    @ResponseBody
    @PostMapping("/sys-user/add")
    public Result userAdd(SysUser sysUser) {
        sysUser.setCreateTime(new Date());
        sysUser.setEnabled(Const.ENABLED_Y);
        // session取得当前用户
        // sysUser.setCreateUserId();
        sysUser.setDelFlag(Const.DEL_FLAG_N);
        Boolean ret = sysUserService.save(sysUser);
        Result r = null;
        if (ret) {
            r = new Result(HttpResultEnum.ADD_CODE_200.getCode(),HttpResultEnum.ADD_CODE_200.getMessage());
        } else {
            r = new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        }
        return r;
    }
}
