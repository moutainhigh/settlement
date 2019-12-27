package com.settlement.vo;

import com.settlement.entity.BaEmployee;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     项目组员工Vo
 * </p>
 *
 * @auth admin
 * @date 2019-11-29
 */
@Data
public class EmployeeVo extends BaEmployee implements Serializable {

    private Integer pgId;

    private String applyUpdateStatusContent;

}
