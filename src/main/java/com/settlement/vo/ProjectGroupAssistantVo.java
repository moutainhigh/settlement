package com.settlement.vo;

import com.settlement.entity.BaProjectGroupAssistant;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 *     项目组助理Vo
 * </p>
 *
 * @auth admin
 * @date 2019-12-19
 */
@Data
public class ProjectGroupAssistantVo extends BaProjectGroupAssistant  implements Serializable {

    private String assistantName;

}
