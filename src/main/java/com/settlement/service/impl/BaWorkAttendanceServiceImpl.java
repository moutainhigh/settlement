package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.TimeParamCo;
import com.settlement.co.WorkAttendanceCo;
import com.settlement.entity.BaTimeParam;
import com.settlement.entity.BaWorkAttendance;
import com.settlement.mapper.BaWorkAttendanceMapper;
import com.settlement.service.BaWorkAttendanceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaWorkAttendanceVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * <p>
 * 考勤表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@Service
public class BaWorkAttendanceServiceImpl extends ServiceImpl<BaWorkAttendanceMapper, BaWorkAttendance> implements BaWorkAttendanceService {

    /**
     * 加载列表页面
     * @param workAttendanceCo
     * @return
     */
    @Override
    public PageData listPageData(WorkAttendanceCo workAttendanceCo) {
        workAttendanceCo.setDelFlag(Const.DEL_FLAG_N);
        workAttendanceCo.setSubStatus(Const.EMP_SUBMIT_STATUS_N);
        Page<BaWorkAttendanceVo> page = new Page<>(workAttendanceCo.getPage(),workAttendanceCo.getLimit());
        List<BaWorkAttendanceVo> baWorkAttendanceVos = this.baseMapper.getWorkAttendanceVoByProjectId(workAttendanceCo,page);
        page.setRecords(baWorkAttendanceVos);
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     *  添加
     * @param baWorkAttendanceVo
     * @return
     */
    @Override
    public Result add(BaWorkAttendanceVo baWorkAttendanceVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        baWorkAttendanceVo.setCreateTime(new Date());
        try {
            Integer ret = this.baseMapper.insert(baWorkAttendanceVo);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     * 修改
     * @param baWorkAttendanceVo
     * @return
     */
    @Override
    public Result update(BaWorkAttendanceVo baWorkAttendanceVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaWorkAttendance> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baWorkAttendanceVo.getId());
        try {
            Integer ret = this.baseMapper.update(baWorkAttendanceVo, updateWrapper);
            if (ret != null && ret > 0) {
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
     * 根据id查询
     * @param id
     * @return
     */
    @Override
    public BaWorkAttendanceVo getBaWorkAttendanceVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("id",id);
        BaWorkAttendanceVo baWorkAttendanceVo = this.baseMapper.getBaWorkAttendanceVoById(map);
        return baWorkAttendanceVo;
    }

    /**
     * 根据年月查询考勤记录
     * @param map
     * @return
     */
    @Override
    public List<BaWorkAttendance> getBaWorkAttendanceVoByNextMonth(Map<String,String> map) {
        map.put("delFlag",Const.DEL_FLAG_N);
        return this.baseMapper.getBaWorkAttendanceVoByNextMonth(map);
    }
}
