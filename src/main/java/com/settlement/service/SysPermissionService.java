package com.settlement.service;

import com.settlement.entity.SysPermission;
import com.baomidou.mybatisplus.extension.service.IService;
import com.settlement.vo.SysPermissionVo;

import java.util.List;

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
}
