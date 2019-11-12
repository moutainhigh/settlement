package com.settlement.controller;


import com.settlement.entity.SysPermission;
import com.settlement.entity.SysUser;
import com.settlement.service.SysPermissionService;
import com.settlement.utils.Result;
import com.settlement.vo.SysPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * @desciption 功能菜单表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Controller
@RequestMapping("/sys-permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @ResponseBody
    @GetMapping("menu")
    public Result getMenuList(HttpSession session) {
        // 从session中取得当前登陆用户角色
        SysUser user = (SysUser)session.getAttribute("user");
        // int roleId = user.getRoles().get(0).getId();
        List<SysPermissionVo> list = sysPermissionService.getMenu(1);
        Result r = new Result();
        r.setData(list);
        return r;
    }

}
