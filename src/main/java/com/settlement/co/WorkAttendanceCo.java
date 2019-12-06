package com.settlement.co;

import lombok.Data;

@Data
public class WorkAttendanceCo extends PageCo{
    private String keyword;
    private Integer checkUserId;
    private Integer projectId;

}
