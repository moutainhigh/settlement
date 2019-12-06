package com.settlement.vo;

import com.settlement.entity.SysDataDic;
import lombok.Data;

@Data
public class SysDataDicListVo extends SysDataDic {
    private Integer childId;
    private String childCode;
    private String childDicContent;
    private Integer childSort;
    private String childEnabled;
    private String childeDelFlag;
}
