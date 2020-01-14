package com.settlement.schedule;

import com.settlement.entity.BaWorkAttendance;
import com.settlement.service.BaWorkAttendanceService;
import com.settlement.utils.Const;
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
//     @Scheduled(cron="0 26 17 13 * ?") //每月1号零点开如执行
//     @Scheduled(cron="*/5 * * * * ?") //每3秒执 行一次
    public void generateWorkAttendance() {
        //查询上个月的所有考勤记录
        Calendar cal = Calendar.getInstance();
        String yearValue=String.valueOf(cal.get(Calendar.YEAR));
        String monthValue=String.valueOf(cal.get(Calendar.MONTH));
         int currentMonth=cal.get(Calendar.MONTH)+1;
         //1月份
         if(1==currentMonth) {
             monthValue="12";
             yearValue=String.valueOf(cal.get(Calendar.YEAR)-1);
         } else {
             yearValue=String.valueOf(cal.get(Calendar.YEAR));
             if(currentMonth<10) {
                 monthValue="0" + currentMonth;
             }
         }

        Map<String,String> map = new HashMap<>();
        map.put("yearValue",yearValue);
        map.put("monthValue",monthValue);
        List<BaWorkAttendance> baWorkAttendances = baWorkAttendanceService.getBaWorkAttendanceVoByNextMonth(map);
         List<BaWorkAttendance> newBaWorkAttendances = new ArrayList<>();
        for(BaWorkAttendance baWorkAttendance:baWorkAttendances) {
            BaWorkAttendance ba = new BaWorkAttendance();
            ba.setWorkDays(baWorkAttendance.getWorkDays());
            ba.setSubStatus(Const.SUB_STATUS_N);
            ba.setPgId(baWorkAttendance.getPgId());
            ba.setEmployeeId(baWorkAttendance.getEmployeeId());
            ba.setCreateTime(new Date());
            newBaWorkAttendances.add(ba);

        }

        boolean ret = baWorkAttendanceService.saveBatch(newBaWorkAttendances);
        if(ret) {
            //生成成功之后记录当前月份生成的记录结果
            System.out.println("----------generateWorkAttendance--------");
        }


    }
}
