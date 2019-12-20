package com.settlement.vo;

import com.settlement.entity.BaProjectGroupCheck;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     项目组审核Vo
 * </p>
 *
 * @auth admin
 * @date 2019-12-10
 */
@Data
public class ProjectGroupCheckVo extends BaProjectGroupCheck implements Serializable {

    private String pgCreateUser;

    private String checkStatusContent;

    private String checkUser;

    private String pgName;

    private String code;

    private String checkResult;

    private Integer customerId;

}
