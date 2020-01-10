package com.settlement.co;

import lombok.Data;

/**
 * @description 用户Co
 *
 * @author admin
 * @date 2019/11/12.
 */
@Data
public class UserCo extends PageCo {
    private String keyword;
    private String delFlag;

}
