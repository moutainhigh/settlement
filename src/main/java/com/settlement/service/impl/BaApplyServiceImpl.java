package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ApplyCo;
import com.settlement.entity.BaApply;
import com.settlement.entity.BaApplyAttendance;
import com.settlement.mapper.BaApplyAttendanceMapper;
import com.settlement.mapper.BaApplyMapper;
import com.settlement.service.BaApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaApplyVo;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 申请修改员工信息表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-04
 */
@Service
@Transactional
public class BaApplyServiceImpl extends ServiceImpl<BaApplyMapper, BaApply> implements BaApplyService {

    @Autowired
    private BaApplyAttendanceMapper baApplyAttendanceMapper;
    /**
     * 加载列表页面
     * @param applyCo
     * @return
     */
    @Override
    public PageData listPageData(ApplyCo applyCo) {
        Page<BaApplyVo> page = new Page<>(applyCo.getPage(),applyCo.getLimit());
        page.setRecords(this.baseMapper.getApplyWorkAttedances(applyCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 修改
     * @param baApplyVo
     * @return
     */
    @Override
    public Result update(BaApplyVo baApplyVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            UpdateWrapper<BaApply> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",baApplyVo.getId());
            Integer ret=this.baseMapper.update(baApplyVo,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 添加申请修改记录
     * @param baApplyVo
     * @return
     */
    @Override
    public Result addAttendance(BaApplyVo baApplyVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            baApplyVo.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
            baApplyVo.setApplyTime(new Date());
            BaApply baApply = new BaApply();
            baApply.setApplyType(baApplyVo.getApplyType());
            baApply.setApplyRemark(baApplyVo.getApplyRemark());
            baApply.setCheckStatus(baApplyVo.getCheckStatus());
            baApply.setApplyTime(new Date());
            baApply.setApplyUser(baApplyVo.getApplyUser());
            baApply.setCheckUser(baApplyVo.getCheckUser());
            baApply.setCheckRemark(baApplyVo.getCheckRemark());

            Integer ret = this.baseMapper.insert(baApply);
            if (ret != null && ret > 0) {
                //添加考勤记录关联表
                List<BaApplyAttendance> list = new ArrayList<>();
                for(Integer applyAttendanceId:baApplyVo.getWorkAttendanceIds()) {
                    BaApplyAttendance baApplyAttendance = new BaApplyAttendance();
                    baApplyAttendance.setApplyId(baApply.getId());
                    baApplyAttendance.setAttendanceId(applyAttendanceId);
                    list.add(baApplyAttendance);
                }
                Integer ret2 = baApplyAttendanceMapper.insertBatch(list);
                if(ret2!=null && ret2>0) {
                    r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     * 申请修改员工记录
     * @param baApplyVo
     * @return
     */
    @Override
    public Result addEmployee(BaApplyVo baApplyVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            baApplyVo.setCheckStatus(Const.CHECK_STATUS_NO_CHECK);
            baApplyVo.setApplyTime(new Date());
            Integer ret = this.baseMapper.insert(baApplyVo);
            if (ret != null && ret > 0) {
                //添加考勤记录关联表
                List<BaApplyAttendance> list = new ArrayList<>();
                for(Integer applyAttendanceId:baApplyVo.getWorkAttendanceIds()) {
                    BaApplyAttendance baApplyAttendance = new BaApplyAttendance();
                    baApplyAttendance.setApplyId(baApplyVo.getId());
                    baApplyAttendance.setAttendanceId(applyAttendanceId);
                    list.add(baApplyAttendance);
                }
                //插入修改员工记录关联表baApplyEmployeeMapper
                Integer ret2 = baApplyAttendanceMapper.insertBatch(list);
                if(ret2!=null && ret2>0) {
                    r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     * 验证口令
     * @param baApply
     * @return
     */
    @Override
    public Result verifyPasscode(BaApply baApply) {
        Result r = new Result(HttpResultEnum.VERIFY_CODE_500.getCode(),HttpResultEnum.VERIFY_CODE_500.getMessage());;
        BaApply baApply1 = this.baseMapper.selectById(baApply.getId());
        if(baApply1!=null && baApply1.getUpdatePassword().trim().equals(baApply.getUpdatePassword().trim())) {
           r.setCode(HttpResultEnum.VERIFY_CODE_200.getCode());
           r.setMsg(HttpResultEnum.VERIFY_CODE_200.getMessage());
           r.setData(baApply.getId());
        }
        return r;
    }
}
