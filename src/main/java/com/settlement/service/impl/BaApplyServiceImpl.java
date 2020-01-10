package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ApplyCo;
import com.settlement.entity.*;
import com.settlement.mapper.BaApplyAttendanceMapper;
import com.settlement.mapper.BaApplyMapper;
import com.settlement.mapper.BaWorkAttendanceMapper;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.service.BaApplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaApplyVo;
import com.settlement.vo.BaWorkAttendanceVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

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
    @Autowired
    private BaWorkAttendanceMapper baWorkAttendanceMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 加载列表页面
     * @param applyCo
     * @return
     */
    @Override
    public PageData listPageData(ApplyCo applyCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        applyCo.setApplyUser(user.getId());
        Page<BaApplyVo> page = new Page<>(applyCo.getPage(),applyCo.getLimit());
        page.setRecords(this.baseMapper.getApplyWorkAttedances(applyCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }
    /**
     * 加载考勤审核列表
     * @param applyCo
     * @return
     */
    @Override
    public PageData listCheckWorkAttendancePageData(ApplyCo applyCo) {
        Page<BaApplyVo> page = new Page<>(applyCo.getPage(),applyCo.getLimit());
        Map<String,Object> roleMap = new HashMap<>();
        roleMap.put("delFlag",Const.DEL_FLAG_N);
        roleMap.put("userId",applyCo.getCheckUser());
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(roleMap);
        applyCo.setRoleCode(sysRole.getRoleCode());
        page.setRecords(this.baseMapper.listCheckWorkAttendancePageData(applyCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 修改通过的考勤信息列表 Assistant
     * @param applyCo
     * @return
     */
    @Override
    public PageData listApplyWorkAttendancelistPageData(ApplyCo applyCo) {
        Page<BaWorkAttendanceVo> page = new Page<>(applyCo.getPage(),applyCo.getLimit());
        BaApply baApply = this.baseMapper.selectById(applyCo.getId());
        applyCo.setUpdateStatus(Const.SUB_STATUS_A);
        //applyCo.setApplyUser(baApply.getApplyUser());
        page.setRecords(this.baseMapper.listApplyWorkAttendancelistPageData(applyCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /***
     * 申请修改考勤--审核详细列表 AM
     * @param applyCo
     * @return
     */
    @Override
    public PageData listApplyCheckWorkAttendancelistPageData(ApplyCo applyCo) {
        Page<BaWorkAttendanceVo> page = new Page<>(applyCo.getPage(),applyCo.getLimit());
        BaApply baApply = this.baseMapper.selectById(applyCo.getId());
        applyCo.setUpdateStatus(Const.SUB_STATUS_A);
        page.setRecords(this.baseMapper.listApplyCheckWorkAttendancelistPageData(applyCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 提交修改的考勤记录
     * @param ids
     * @return
     */
    @Override
    public Result commitWorkAttendance(Integer[] ids,String applyId) {
        Result r = new Result(HttpResultEnum.COMMIT_CODE_500.getCode(),HttpResultEnum.COMMIT_CODE_500.getMessage());
        List<BaApplyAttendance> baApplyAttendances = new ArrayList<>();
        List<BaWorkAttendance> baWorkAttendances = new ArrayList<>();
        for(Integer id:ids) {
            QueryWrapper<BaApplyAttendance> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("attendance_id",id);
            queryWrapper.eq("apply_id",applyId);
            BaApplyAttendance baApplyAttendance = baApplyAttendanceMapper.selectOne(queryWrapper);
            baApplyAttendance.setUpdateStatus(Const.SUB_STATUS_S);
            baApplyAttendances.add(baApplyAttendance);
            BaWorkAttendance baWorkAttendance = baWorkAttendanceMapper.selectById(id);
            baWorkAttendance.setSubStatus(Const.SUB_STATUS_S);
            baWorkAttendances.add(baWorkAttendance);
        }
        if(baApplyAttendances.size()>0) {
            Integer ret = baApplyAttendanceMapper.updateStatusBatch(baApplyAttendances);
            if(ret!=null && ret>0 && baWorkAttendances.size()>0) {
                Integer ret2 = baWorkAttendanceMapper.updateSubStatusBatch(baWorkAttendances);
                if(ret2!=null && ret>0) {
                    r.setCode(HttpResultEnum.COMMIT_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.COMMIT_CODE_200.getMessage());
                }
            }
        }
        return r;
    }

    /**
     * 检查每一条申请记录中全部是否还有未修改完的数据
     * @param applyId
     * @return
     */
    @Override
    public Result checkApplyStatus(Integer applyId) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        QueryWrapper<BaApplyAttendance> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("apply_id",applyId);
        queryWrapper.eq("update_status",Const.SUB_STATUS_A);
        List<BaApplyAttendance> baApplyAttendances = baApplyAttendanceMapper.selectList(queryWrapper);
        if(baApplyAttendances==null || baApplyAttendances.size()==0) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
        }
        return r;
    }

    /**
     * 修改
     * @param baApplyVo
     * @return
     */
    @Override
    public Result updateCheck(BaApplyVo baApplyVo) {
        Result r = new Result(HttpResultEnum.CHK_CODE_500.getCode(),HttpResultEnum.CHK_CODE_500.getMessage());
        try{
            if(Const.CHECK_RESULT_PASS_CODE.equals(baApplyVo.getCheckResult())) {
                baApplyVo.setCheckStatus(Const.CHECK_STATUS_CHECK_PASS);
            } else if(Const.CHECK_RESULT_NOPASS_CODE.equals(baApplyVo.getCheckResult())){
                baApplyVo.setCheckStatus(Const.CHECK_STATUS_CHECK_NOPASS);
            }
            baApplyVo.setCheckTime(new Date());
            UpdateWrapper<BaApply> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",baApplyVo.getId());
            Integer ret=this.baseMapper.update(baApplyVo,updateWrapper);
            if(ret!=null && ret>0) {
                if(Const.CHECK_RESULT_NOPASS_CODE.equals(baApplyVo.getCheckResult())){
                    //审核未通过更新考勤记录
                    QueryWrapper<BaApplyAttendance> queryWrapper = new QueryWrapper<>();
                    queryWrapper.eq("apply_id",baApplyVo.getId());
                    List<BaApplyAttendance> baApplyAttendances = baApplyAttendanceMapper.selectList(queryWrapper);
                    for(BaApplyAttendance baApplyAttendance : baApplyAttendances) {
                        Integer attendId = baApplyAttendance.getAttendanceId();
                        UpdateWrapper<BaWorkAttendance> updateWrapper2 = new UpdateWrapper<>();
                        updateWrapper2.set("sub_status",Const.SUB_STATUS_N);
                        updateWrapper2.eq("id",attendId);
                        this.baWorkAttendanceMapper.update(baWorkAttendanceMapper.selectById(attendId),updateWrapper2);
                    }
                }
                r.setCode(HttpResultEnum.CHK_CODE_200.getCode());
                r.setMsg(HttpResultEnum.CHK_CODE_200.getMessage());
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
            baApply.setPgId(baApplyVo.getPgId());
            baApply.setApplyTime(new Date());
            baApply.setApplyUser(baApplyVo.getApplyUser());
            baApply.setCheckUser(baApplyVo.getCheckUser());
            baApply.setCheckRemark(baApplyVo.getCheckRemark());

            Integer ret = this.baseMapper.insert(baApply);
            if (ret != null && ret > 0) {
                List<BaWorkAttendance> baWorkAttendances = new ArrayList<>();
                //添加考勤记录关联表
                List<BaApplyAttendance> list = new ArrayList<>();
                for(Integer applyAttendanceId:baApplyVo.getWorkAttendanceIds()) {
                    BaApplyAttendance baApplyAttendance = new BaApplyAttendance();
                    baApplyAttendance.setApplyId(baApply.getId());
                    baApplyAttendance.setAttendanceId(applyAttendanceId);
                    baApplyAttendance.setUpdateStatus(Const.SUB_STATUS_A);
                    baApplyAttendance.setOperTime(new Date());
                    list.add(baApplyAttendance);
                    BaWorkAttendance baWorkAttendance = baWorkAttendanceMapper.selectById(applyAttendanceId);
                    baWorkAttendance.setSubStatus(Const.SUB_STATUS_A);
                    baWorkAttendances.add(baWorkAttendance);
                }
                Integer ret2 = baApplyAttendanceMapper.insertBatch(list);
                Integer ret3 = baWorkAttendanceMapper.updateSubStatusBatch(baWorkAttendances);
                if(ret2!=null && ret2>0 && ret3!=null && ret3>0) {
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

    /**
     * 获得每个项目的申请修改的次数
     * @param projectId
     * @return
     */
    @Override
    public Result getApplyCountByProjectId(Integer projectId,String monthValue) {
        Map<String,Object> map = new HashMap<>();
        map.put("projectId",projectId);
        map.put("applyType",Const.APPLY_TYPE);
        map.put("applyTime",monthValue);
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        Integer count = this.baseMapper.getApplyCountByProjectId(map);
        if(count!=null) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(count);
        }
        return  r;
    }

    /**
     * 根据id 获得BaApplyVo
     * @param id
     * @return
     */
    @Override
    public BaApplyVo getApplyVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        return this.baseMapper.getApplyVoById(map);
    }

    /**
     * 审核考勤修改
     * @param baApplyVo
     * @return
     */
    @Override
    public Result checkWorkattend(BaApplyVo baApplyVo) {
        Result r = new Result(HttpResultEnum.CHK_CODE_500.getCode(),HttpResultEnum.CHK_CODE_500.getMessage());
        BaApply baApply = new BaApply();
        BeanUtils.copyProperties(baApplyVo,baApply);
        UpdateWrapper<BaApply> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("update_password",baApplyVo.getUpdatePassword());
        updateWrapper.set("check_remark",baApplyVo.getCheckRemark());
        updateWrapper.set("check_status",baApplyVo.getCheckStatus());
        updateWrapper.set("check_time",new Date());
        updateWrapper.eq("id",baApplyVo.getId());
        Integer ret =  this.baseMapper.update(baApply,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.CHK_CODE_200.getCode());
            r.setMsg(HttpResultEnum.CHK_CODE_200.getMessage());
        }
        return r;
    }




}
