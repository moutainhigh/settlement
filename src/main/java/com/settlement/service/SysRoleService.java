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
 * @author admin
 * @since 2019-11-07
 */
public interface SysRoleService extends IService<SysRole> {

    List<SysRole> findRoleByUserId(Integer userId);

    Result getRolesByDeptId(Integer deptId);

    Result saveRole(SysRoleVo sysRoleVo);

    Result updateRole(SysRoleVo sysRoleVo);
    
    Result deleteRole(Integer id);

    Result roleCodeIsExist(String roleCode);

    PageData listPageData(RoleCo roleCo);
}
