package com.settlement.vo;

import com.settlement.entity.SysDept;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class SysDeptVo extends SysDept {
    private String checkArr;
    private String title;
    private List<SysPermissionVo> childrens ;
    private boolean open;
    private boolean checked;
    public List<SysPermissionVo> getChildrens() {
        if(childrens==null) {
            childrens = new ArrayList<>();
        }
        return childrens;
    }

    public void setChildrens(List<SysPermissionVo> childrens) {
        this.childrens = childrens;
    }
}
