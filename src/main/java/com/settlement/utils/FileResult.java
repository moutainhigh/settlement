package com.settlement.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     文件上传返回类
 * </p>
 *
 * @auth admin
 * @date 2019-12-3
 */
@Data
public class FileResult extends Result implements Serializable {

    private String path;

    private String fileName;

}
