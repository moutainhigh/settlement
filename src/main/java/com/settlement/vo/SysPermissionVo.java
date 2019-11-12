package com.settlement.vo;

import com.settlement.entity.SysPermission;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 菜单Vo
 *
 * @author admin
 * @date 2019/11/11.
 */
@Data
public class SysPermissionVo extends SysPermission {

    private List<SysPermissionVo> childrens = new ArrayList<SysPermissionVo>();

}
