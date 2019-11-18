package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.UserCo;
import com.settlement.config.RemoteProperties;
import com.settlement.entity.SysUser;
import com.settlement.entity.SysUserRole;
import com.settlement.service.SysDeptService;
import com.settlement.service.SysUserRoleService;
import com.settlement.service.SysUserService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
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

@RestController
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;


    /**
     * @description 用户列表
     *
     * @param userCo    用户查询条件
     * @param model
     * @return
     */
    @GetMapping("/sys-user/pagedata")
    public PageData getUserList(UserCo userCo, Model model) {
        IPage<SysUser> page = new Page<SysUser>(userCo.getPage(), userCo.getLimit());
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.like(StringUtils.isNotBlank(userCo.getKeyword()),"real_name",userCo.getKeyword());
        queryWrapper.orderByDesc("create_time");
        this.sysUserService.page(page, queryWrapper);
        return new PageData(page.getTotal(), page.getRecords());
    }

    /**
     * @description  用户新增
     *
     * @param sysUser            用户信息
     * @param request
     * @return
     */
    @PostMapping("/sys-user/add")
    public Result userAdd(SysUserVo sysUser, HttpServletRequest request) {
        sysUser.setCreateTime(new Date());
        sysUser.setEnabled(Const.ENABLED_Y);
        sysUser.setSalt(Const.SALT);
        // session取得当前用户
      //  SysUser loginuser = (SysUser)request.getSession().getAttribute("user");
      //  sysUser.setCreateUserId(loginuser.getId());
        sysUser.setDelFlag(Const.DEL_FLAG_N);
        Result r = this.sysUserService.saveUser(sysUser);
        return r;
    }

    /**
     * @description 用户编辑
     *
     * @param sysUser              用户信息
     * @return
     */
    @PutMapping("/sys-user/edit")
    public Result userEdit(SysUserVo sysUser) {
        return this.sysUserService.updateUser(sysUser);
    }

    /**
     * @description 检查邮箱是否存在
     *
     * @param email               邮箱
     * @return
     */
    @GetMapping("/sys-user/isexist/{email}")
    public Result userIsExist(@PathVariable  String email) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<SysUser>();
        queryWrapper.eq("email",email);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        Result r = null;
        if (sysUser == null) {
            r = new Result(HttpResultEnum.USER_ISEXIST_0 .getCode(),HttpResultEnum.USER_ISEXIST_0.getMessage());
        } else {
            r = new Result(HttpResultEnum.USER_ISEXIST_1 .getCode(),HttpResultEnum.USER_ISEXIST_1.getMessage());
        }
        return r;
    }

    /**
     * @description  用户删除
     *
     * @param id             用户ID
     * @return
     */
    @DeleteMapping("/sys-user/del/{id}")
    public Result userDelete(@PathVariable Integer id) {
        // 如果启用中，不能删除

        return this.sysUserService.deleteUser(id);
    }

    /**
     * @description 重置密码
     *
     * @param id               用户ID
     * @return
     */
    @GetMapping("/sys-user/resetpass/{id}")
    public Result resetPass(@PathVariable  Integer id) {
        return sysUserService.updateUserDefaultPassword(id);
    }

    /**
     * @description 启用
     *
     * @param id              用户ID
     * @return
     */
    @GetMapping("/sys-user/start/{id}")
    public Result userStart(@PathVariable  Integer id) {
        return sysUserService.updateUserStart(id);
    }


}
