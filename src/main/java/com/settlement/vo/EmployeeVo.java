package com.settlement.vo;

import com.settlement.entity.BaEmployee;
import lombok.Data;

/**
 * <p>
 *     项目组员工Vo
 * </p>
 *
 * @auth admin
 * @date 2019-11-29
 */
@Data
public class EmployeeVo extends BaEmployee {

    private Integer pgId;

}
