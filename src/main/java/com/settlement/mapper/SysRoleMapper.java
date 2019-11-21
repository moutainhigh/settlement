package com.settlement.mapper;

import com.settlement.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT r.id, r.role_cn_name, r.role_code  FROM sys_role r, sys_user_role ur WHERE r.del_flag = 'N' ur.role_id = r.id AND ur.user_id = #{userId}")
    List<SysRole> getSysRoleByUserId(Integer userId);

    @Select("SELECT sr.id, sr.role_cn_name,sr.role_code FROM sys_role sr,sys_dept_role sdr WHERE sr.del_flag = 'N'and sr.id = sdr.role_id and sdr.dept_id = #{deptId}")
    List<SysRole> getRolesByDeptId(Integer deptId);

}
