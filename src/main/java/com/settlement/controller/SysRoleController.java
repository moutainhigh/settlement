package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.RoleCo;
import com.settlement.entity.SysPermission;
import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import com.settlement.service.SysPermissionRoleService;
import com.settlement.service.SysPermissionService;
import com.settlement.service.SysRoleService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/sys-role")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysPermissionService sysPermissionService;
    @Autowired
    private SysPermissionRoleService sysPermissionRoleService;
    @ResponseBody
    @GetMapping("/dept/{deptId}")
    public Result getRolesByDeptId(@PathVariable Integer deptId) {
        return sysRoleService.getRolesByDeptId(deptId);
    }

    /**
     * 查询角色列表页面
     * @param roleCo
     * @param model
     * @return
     */

    @GetMapping("/pagedata")
    public PageData listPageData(RoleCo roleCo, Model model) {
        PageData pageData = sysRoleService.listPageData(roleCo);
        return pageData;
    }

    /**
     * 添加角色
     * @param sysRoleVo
     * @return
     */

    @PostMapping("/add")
    public Result roleAdd(SysRoleVo sysRoleVo, HttpSession session) {
        SysUser user = (SysUser)session.getAttribute("user");
        sysRoleVo.setCreateUserId(user.getId());
        Result r=sysRoleService.saveRole(sysRoleVo);
        if(r.getCode().equals(HttpResultEnum.ADD_CODE_200.getCode())) {
            SysRole sysRole=(SysRole)r.getData();
            sysRoleVo.setId(sysRole.getId());
            sysPermissionRoleService.savePermissionRoles(sysRoleVo);
        }
        return r;
    }
    /**
     * 修改角色
     * @param sysRoleVo
     * @return
     */

    @PostMapping("/update")
    public Result roleUpdate(SysRoleVo sysRoleVo) {
        Result r=sysRoleService.updateRole(sysRoleVo);
        if(r.getCode().equals(HttpResultEnum.EDIT_CODE_200.getCode())) {
            sysPermissionRoleService.savePermissionRoles(sysRoleVo);
        }
        return r;
    }

    /**
     * 角色删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result deleteRole(@PathVariable(value="id") Integer id) {
        Result r = sysRoleService.deleteRole(id);
        return r;
    }

    /**
     * 检查角色编码roleCode是否存在
     * @param roleCode
     * @return
     */
    @GetMapping("/isexist/{roleCode}")
    public Result roleCodeIsExist(@PathVariable(value="roleCode") String roleCode){
        Result r =  sysRoleService.roleCodeIsExist(roleCode);
        return  r;
    }
}
