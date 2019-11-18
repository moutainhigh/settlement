package com.settlement.vo;

import lombok.Data;

import java.util.List;

/**
 * @description 部门下拉框
 *
 * @author  admin
 * @date 2019/11/13.
 */
@Data
public class DeptSelectVo {

    private Integer value;

    private String name;

    private List<DeptSelectVo> children;

    public DeptSelectVo(Integer value, String name) {
        this.value = value;
        this.name = name;
    }
}
