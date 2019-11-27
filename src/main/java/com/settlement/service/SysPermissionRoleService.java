package com.settlement.service;

import com.settlement.entity.SysPermissionRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.SysRoleVo;

/**
 * <p>
 * 菜单角色关联表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-26
 */
public interface SysPermissionRoleService extends IService<SysPermissionRole> {

    Result savePermissionRoles(SysRoleVo sysRoleVo);
}
