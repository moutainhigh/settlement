package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.BaApplyAttendance;
import com.settlement.mapper.BaApplyAttendanceMapper;
import com.settlement.service.BaApplyAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * <p>
 * 申请考勤关联 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@Service
@Transactional
public class BaApplyAttendanceServiceImpl extends ServiceImpl<BaApplyAttendanceMapper, BaApplyAttendance> implements BaApplyAttendanceService {

    /**
     * 添加申请修改考勤记录关联表
     * @param baApplyAttendance
     * @return
     */
    @Override
    public Result add(BaApplyAttendance baApplyAttendance) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            Integer ret = this.baseMapper.insert(baApplyAttendance);
            if(ret != null && ret >0){
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 检查数据是否有修改中的数据,不能提交
     * @param ids
     * @return
     */
    @Override
    public Result checkApplyModify(Integer[] ids) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        QueryWrapper<BaApplyAttendance> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("attendance_id",ids);
        List<BaApplyAttendance> baApplyAttendances = this.baseMapper.selectList(queryWrapper);
        if(baApplyAttendances!=null && baApplyAttendances.size()>0) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(baApplyAttendances);
        }
        return r;
    }
}
