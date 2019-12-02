package com.settlement.service;

import com.settlement.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.utils.Result;
import com.settlement.vo.SysPermissionVo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 功能菜单表 服务类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
public interface SysPermissionService extends IService<SysPermission> {
    /**根据id获得对应的子菜单**/
    List<SysPermissionVo> getMenu(Integer roleId);
    /**菜单树列表**/
    List<SysPermissionVo> getMenu();
    /****/
    Map<SysPermission,List<Map<SysPermission,List<SysPermission>>>> getPermissons();
    /****/
    Map<SysPermissionVo, List<Map<SysPermissionVo, List<SysPermissionVo>>>> getPermissons(Integer id);
    /**添加**/
    Result add(SysPermission sysPermission);
    /**添加**/
    Result update(SysPermission sysPermission);
    /**添加**/
    Result delete(Integer id);


}
