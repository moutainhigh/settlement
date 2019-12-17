package com.settlement.vo;

import lombok.Data;

@Data
public class SysDeptRoleUserVo {
    private Integer deptId;
    private String deptName;
    private Integer roleId;
    private String roleCnName;
    private Integer userId;
    private String userRealName;
}
