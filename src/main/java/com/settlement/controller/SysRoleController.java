package com.settlement.controller;


import com.settlement.service.SysRoleService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Controller
@RequestMapping("/sys-role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    @ResponseBody
    @GetMapping("/dept/{deptId}")
    public Result getRolesByDeptId(@PathVariable Integer deptId) {
        return sysRoleService.getRolesByDeptId(deptId);
    }


}
