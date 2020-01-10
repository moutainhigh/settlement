package com.settlement.mapper;

import com.settlement.entity.SysPermission;
import com.settlement.entity.SysPermissionRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 菜单角色关联表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-26
 */
@Repository
public interface SysPermissionRoleMapper extends BaseMapper<SysPermissionRole> {
      /**根据roleId获得对应的permission**/
      @Select("select " +
              " * " +
              " from sys_permission p" +
              " left join sys_permission_role pr  on p.id = pr.permission_id" +
              " where pr.role_id =#{roleId}")
      List<SysPermission> getPermissionByRoleId(Integer roleId);


}
