package com.settlement.co;

import lombok.Data;

/**
 * <p>
 *  项目组审核Co
 * </p>
 *
 * @auth admin
 * @date 2019-12-10
 */
@Data
public class ProjectGroupCheckCo extends PageCo {

    private String keyword;
    /** 当前登陆用户 */
    private Integer currentUserId;

    private String delFlag;
}
