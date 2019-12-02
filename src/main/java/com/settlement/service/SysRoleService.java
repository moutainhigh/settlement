package com.settlement.service;

import com.settlement.bo.PageData;
import com.settlement.co.RoleCo;
import com.settlement.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.SysRoleVo;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
public interface SysRoleService extends IService<SysRole> {
    /**根据角色查询用户**/
    List<SysRole> findRoleByUserId(Integer userId);
    /**根据角色查询部门**/
    Result getRolesByDeptId(Integer deptId);
    /**添加角色**/
    Result saveRole(SysRoleVo sysRoleVo);
    /**修改角色**/
    Result updateRole(SysRoleVo sysRoleVo);
    /**删除角色**/
    Result deleteRole(Integer id);
    /**检查是否在角色**/
    Result roleCodeIsExist(String roleCode);
    /**角色列表页**/
    PageData listPageData(RoleCo roleCo);
    /**查询sysRoleVo**/
    List<SysRoleVo> getRoleVo();
    /**查询部门所对应的sysRoleVo**/
    List<SysRoleVo> getRoleVoByDeptId(Integer deptId);
}
