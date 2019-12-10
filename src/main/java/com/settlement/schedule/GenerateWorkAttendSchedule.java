package com.settlement.schedule;

import com.settlement.entity.BaWorkAttendance;
import com.settlement.service.BaWorkAttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

/***
 * @author kun
 * 定时任务：每月1号生根据上月考勤记录生成本月基础考勤记录
 */

@Component
public class GenerateWorkAttendSchedule {

    @Autowired
    BaWorkAttendanceService baWorkAttendanceService;

    /**
     * 每月1号零点开如执行
     */
     @Scheduled(cron="0 0 0 1 * ?") //每月1号零点开如执行
     //@Scheduled(cron="*/3 * * * * ?") //每3秒执行一次
    public void generateWorkAttendance() {
        //查询上个月的所有考勤记录
        Calendar cal = Calendar.getInstance();
        String yearValue=String.valueOf(cal.get(Calendar.YEAR));
        String monthValue=String.valueOf(cal.get(Calendar.MONTH));
        Map<String,String> map = new HashMap<>();
        map.put("yearValue",yearValue);
        map.put("monthValue",monthValue);
        List<BaWorkAttendance> baWorkAttendances = baWorkAttendanceService.getBaWorkAttendanceVoByNextMonth(map);
        for(BaWorkAttendance baWorkAttendance:baWorkAttendances) {
            baWorkAttendance.setId(null);
            baWorkAttendance.setCreateTime(new Date());
        }

        boolean ret = baWorkAttendanceService.saveBatch(baWorkAttendances);
        if(ret) {
            //生成成功之后记录当前月份生成的记录结果
            System.out.println("----------generateWorkAttendance--------");
        }


    }
}
