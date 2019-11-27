package com.settlement.vo;

import com.settlement.entity.SysRole;
import lombok.Data;

@Data
public class SysRoleVo extends SysRole {
    private Integer[] sysPermissions;
}
