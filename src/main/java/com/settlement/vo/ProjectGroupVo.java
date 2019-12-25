package com.settlement.vo;

import com.settlement.entity.BaProjectGroup;
import lombok.Data;

import java.io.Serializable;

/**
 * @description 项目组Vo
 *
 * @auth admin
 * @date 2019-11-20
 */
@Data
public class ProjectGroupVo extends BaProjectGroup implements Serializable {
    /** 审核状态数据字典内容 */
    private String checkStatusContent;
    /** 负责人姓名 */
    private String ownerName;

    private String assistants;

    private String settlements;

    private String checkUserName;

}
