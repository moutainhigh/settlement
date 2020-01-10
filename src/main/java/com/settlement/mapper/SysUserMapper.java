package com.settlement.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.UserCo;
import com.settlement.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.settlement.utils.Result;
import com.settlement.vo.SysUserVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Repository
public interface SysUserMapper extends BaseMapper<SysUser> {
    /**
     * @description 根据邮箱检索用户
     *
     * @auth admin
     * @date 2019-11-22
     * @param email
     * @return
     */
    @Select("SELECT su.id, su.email, su.password,su.employee_no,su.real_name, su.dept_id, su.salt FROM sys_user su WHERE su.del_flag = 'N' and su.email = #{email}")
    SysUser getUserByEmail(String email);

    /**
     * @description 根据部门ID，角色ID，查询用户
     *
     * @param roleId
     * @param userId
     * @param deptId
     * @return
     * @auth admin
     * @date 2019-11-22
     */
    @Select("SELECT su.id, su.real_name FROM sys_user su, sys_user_role sur WHERE su.id = sur.user_id and su.enabled = 'Y' and sur.role_id = #{roleId} and su.dept_id = #{deptId} and su.id != #{userId}")
    List<SysUser> getUserSelectByRoleDept(@Param(value="roleId") Integer roleId, @Param(value="userId") Integer userId, @Param(value="deptId") Integer deptId);

    /**
     * @description 客户经理下拉框
     *
     * @param map
     * @return
     * @auth admin
     * @date 2019-11-22
     */
    List<SysUser> getAmSelect(Map<String, Object> map);

    /**
     * @description 助理下拉框
     *
     * @param map
     * @return
     * @auth admin
     * @date 2019-11-22
     */
    List<SysUser> getAssistantSelect(Map<String, Object> map);

    /**
     * @description 结算负责人下拉框
     *
     * @param map
     * @return
     */
    List<SysUser> getSettlementSelect(Map<String, Object> map);
    /**用户列表**/
    List<SysUserVo> getUserList(UserCo userCo, Page<SysUserVo> page);
    /** 根据deptId和roleCode获得当前部门下的AM角色的用户 **/
    List<SysUserVo> getUserByDeptIdAndRoleCode(Map<String, Object> map);

}
