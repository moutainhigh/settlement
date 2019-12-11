package com.settlement.vo;

import com.settlement.entity.BaProjectGroupCheck;
import lombok.Data;

/**
 * <p>
 *     项目组审核Vo
 * </p>
 *
 * @auth admin
 * @date 2019-12-10
 */
@Data
public class ProjectGroupCheckVo extends BaProjectGroupCheck {

    private String pgCreateUser;

    private String checkStatusContent;

    private String checkUser;

}
