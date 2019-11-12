package com.settlement.utils;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @description 返回结果对象
 *
 * @author admin
 * @date 2019/11/08.
 */
@Data
public class Result<T extends Object>  implements Serializable {

    private String code;

    private String msg;

    private T data;

    public Result() {

    }

    public Result(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
