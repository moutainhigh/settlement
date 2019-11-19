package com.settlement.mapper;

import com.settlement.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT su.id, su.email, su.password, su.salt FROM sys_user su WHERE su.del_flag = 'N' and su.email = #{email}")
    SysUser getUserByEmail(String email);

    @Select("SELECT su.id, su.real_name FROM sys_user su, sys_user_role sur WHERE su.id = sur.user_id and su.enabled = 'Y' and sur.role_id = #{roleId} and su.dept_id = #{deptId} and su.id != #{userId}")
    List<SysUser> getUserSelectByRoleDept(@Param(value="roleId") Integer roleId, @Param(value="userId") Integer userId, @Param(value="deptId") Integer deptId);

}
