package com.settlement.mapper;

import com.settlement.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

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

}
