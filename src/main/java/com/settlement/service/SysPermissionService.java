package com.settlement.service;

import com.settlement.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.vo.SysPermissionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能菜单表 服务类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
public interface SysPermissionService extends IService<SysPermission> {

    List<SysPermissionVo> getMenu(Integer roleId);
    List<SysPermissionVo> getMenu();
    Map<SysPermission,List<Map<SysPermission,List<SysPermission>>>> getPermissons();
    Map<SysPermissionVo, List<Map<SysPermissionVo, List<SysPermissionVo>>>> getPermissons(Integer id);
}
