package com.settlement.vo;

import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2019/11/11.
 */
@Data
public class SysUserVo extends SysUser {

    private SysRole role;

    public SysUserVo() {
    }

    public SysUserVo(Integer id, String email, String password, String employeeNo, String realName, String city, String mobile, String enabled, Integer createUserId, Date createTime, Integer deptId, String delFlag, String salt) {
        super(id, email, password, employeeNo, realName, city, mobile, enabled, createUserId, createTime, deptId, delFlag, salt);
    }
}
