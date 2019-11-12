package com.settlement.bo;

import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.HttpStateEnum;
import lombok.Data;

import java.util.List;

/**
 * @description 页面数据
 *
 * @author admin
 * @date 2019/11/12.
 */
@Data
public class PageData<T, Object> {

    private Integer code;

    private Long count;

    List<T> data;

    public PageData(Long count, List<T> data) {
        this.code = 0;
        this.count = count;
        this.data = data;
    }
}
