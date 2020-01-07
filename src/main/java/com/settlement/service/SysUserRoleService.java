package com.settlement.service;

import com.settlement.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-14
 */
public interface SysUserRoleService extends IService<SysUserRole> {
    /** 根据用户ID取得角色 */
    SysUserRole getRoleByUserId(Integer userId);

}
