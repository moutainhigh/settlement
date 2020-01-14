package com.settlement.vo;

import com.settlement.entity.BaWorkAttendance;
import lombok.Data;

@Data
public class BaWorkAttendanceVo extends BaWorkAttendance {
    private String employeeName;
    private String employeeCode;
    private String updateStatus;
    private String subStatusContent;
}
