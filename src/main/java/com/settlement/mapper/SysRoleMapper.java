package com.settlement.mapper;

import com.settlement.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.vo.SysRoleVo;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Repository
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT r.id, r.role_cn_name,r.role_en_name,r.role_code,r.remark,r.create_user_id,r.create_time,r.del_flag  FROM sys_role r, sys_user_role ur WHERE r.del_flag = #{delFlag} and ur.role_id = r.id AND ur.user_id = #{userId}")
    SysRole getSysRoleByUserId(Map<String,Object> map);

    @Select("SELECT sr.id, sr.role_cn_name,sr.role_code FROM sys_role sr,sys_dept_role sdr WHERE sr.del_flag = #{delFlag} and sr.id = sdr.role_id and sdr.dept_id = #{deptId}")
    List<SysRole> getRolesByDeptId(Map<String,Object> map);

    @Select("select * from sys_role r where r.del_flag=#{delFlag}")
    List<SysRoleVo> getRoleVo(Map<String,Object> map);

    @Select("select r.*,dr.dept_id from sys_role r left join sys_dept_role dr" +
            " on r.id=dr.role_id" +
            " where r.del_flag=#{delFlag}" +
            " and dr.dept_id=#{deptId}")
    List<SysRoleVo> getRoleVoByDeptId(Map<String,Object> map);

}
