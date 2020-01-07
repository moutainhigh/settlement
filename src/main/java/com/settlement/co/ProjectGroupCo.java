package com.settlement.co;

import lombok.Data;

/**
 * @description  项目组查询Co
 *
 * @auth admin
 * @date 2019-11-20
 */
@Data
public class ProjectGroupCo extends PageCo {

    private String keyword;
    /** 当前登陆用户ID */
    private Integer currentUserId;

}
