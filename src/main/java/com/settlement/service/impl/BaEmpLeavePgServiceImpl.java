package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.controller.BaEmpLeavePgController;
import com.settlement.entity.BaEmpLeavePg;
import com.settlement.mapper.BaApplyEmployeeMapper;
import com.settlement.mapper.BaEmpLeavePgMapper;
import com.settlement.mapper.BaEmployeeMapper;
import com.settlement.mapper.BaPgEmpMapper;
import com.settlement.service.BaEmpLeavePgService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmpLeavePgVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-29
 */
@Service
@Transactional
public class BaEmpLeavePgServiceImpl extends ServiceImpl<BaEmpLeavePgMapper, BaEmpLeavePg> implements BaEmpLeavePgService {

    private static Logger logger = LoggerFactory.getLogger(BaEmpLeavePgServiceImpl.class);
    @Autowired
    private BaApplyEmployeeMapper baApplyEmployeeMapper;
    @Autowired
    private BaEmployeeMapper baEmployeeMapper;
    @Autowired
    private BaPgEmpMapper baPgEmpMapper;

    @Override
    public Result saveEmpLeavePg(EmpLeavePgVo empLeavePgVo) {
        logger.info("员工离场保存BaEmpLeavePgServiceImpl: saveEmpLeavePg");
        Result r = new Result();
        try {
            empLeavePgVo.setCreateTime(new Date());
            int ret = this.baseMapper.insert(empLeavePgVo);
            if (ret > 0) {
                r.setCode(HttpResultEnum.CODE_200.getCode());
                r.setMsg(HttpResultEnum.CODE_200.getMessage());
            }
        } catch (Exception e) {
            logger.error("员工离场保存BaEmpLeavePgServiceImpl: saveEmpLeavePg异常" + e.getMessage());
            e.printStackTrace();
            r.setCode(HttpResultEnum.CODE_500.getCode());
            r.setMsg(HttpResultEnum.CODE_500.getMessage());
        }
        return r;
    }

    @Override
    public Result submitEmpLeavePg(EmpLeavePgVo empLeavePgVo) {
        logger.info("员工离场提交BaEmpLeavePgServiceImpl: submitEmpLeavePg");
        Result r = new Result();
        // 判断ba_emp_leave_pg表中是否存在、更新ba_emp_leave_pg
        QueryWrapper<BaEmpLeavePg> queryWrapper = new QueryWrapper<BaEmpLeavePg>();
        queryWrapper.eq("pg_emp_id", empLeavePgVo.getPgEmpId());
        int count = this.baseMapper.selectCount(queryWrapper);
        int ret = 0;
        if (count > 0) {
            ret = this.baseMapper.insert(empLeavePgVo);
        } else {
            UpdateWrapper<BaEmpLeavePg> updateWrapper = new UpdateWrapper<BaEmpLeavePg>();
            updateWrapper.eq("pg_emp_id", empLeavePgVo.getPgEmpId());
            ret = this.baseMapper.update(empLeavePgVo,updateWrapper);
        }
        // 更新ba_employee entrance_status

        // 更新ba_pg_emp entrance_status

        // ba_apply_employee表 update_status

        return r;
    }
}
