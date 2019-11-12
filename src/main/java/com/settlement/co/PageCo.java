package com.settlement.co;

import lombok.Data;

/**
 * @description PageCo
 *
 * @author admin
 * @date 2019/11/12.
 */
@Data
public abstract class PageCo {

    Integer page;

    Integer limit;
}
