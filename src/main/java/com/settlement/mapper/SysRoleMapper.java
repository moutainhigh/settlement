package com.settlement.mapper;

import com.settlement.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT r.id, r.role_cn_name, r.role_code  FROM sys_role r, sys_user_role ur WHERE ur.role_id = r.id AND ur.user_id = #{userId}")
    List<SysRole> getSysRoleByUserId(Integer userId);

}
