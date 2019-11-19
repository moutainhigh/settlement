package com.settlement.controller;


import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

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


    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 查询角色列表页面
     * @param roleCo
     * @param model
     * @return
     */
    @ResponseBody
    @GetMapping("/pagedata")
    public PageData getRoleList(RoleCo roleCo, Model model) {
        IPage<SysRole> page = new Page<>(roleCo.getPage(),roleCo.getLimit());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleCo.getKeyword()),"role_cn_name",roleCo.getKeyword());
        sysRoleService.page(page,queryWrapper);
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 跳转角色添加页面
     * @return
     */
    @GetMapping("/toadd")
    public String toRoleAdd() {
        return "role/role-add";
    }
}
