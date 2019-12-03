package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.TimeParamCo;
import com.settlement.entity.BaTimeParam;
import com.settlement.mapper.BaTimeParamMapper;
import com.settlement.service.BaTimeParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * <p>
 * 时间点参数表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-27
 */
@Service
@Transactional
public class BaTimeParamServiceImpl extends ServiceImpl<BaTimeParamMapper, BaTimeParam> implements BaTimeParamService {
    /**
     * 加载列表页面
     * @param timeParamCo
     * @return
     */
    @Override
    public PageData listPageData(TimeParamCo timeParamCo) {
        Page<BaTimeParam> page = new Page<>(timeParamCo.getPage(),timeParamCo.getLimit());
        QueryWrapper<BaTimeParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.like(StringUtils.isNotBlank(timeParamCo.getKeyword()),"param_value",timeParamCo.getKeyword());
        this.baseMapper.selectPage(page,queryWrapper);

        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     *  添加
     * @param baTimeParam
     * @return
     */
    @Override
    public Result addTimeParam(BaTimeParam baTimeParam) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        baTimeParam.setDelFlag(Const.DEL_FLAG_N);
        baTimeParam.setEnabled(Const.ENABLED_Y);
        baTimeParam.setCreateTime(new Date());
        try {
            Integer ret = this.baseMapper.insert(baTimeParam);
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
     * @param baTimeParam
     * @return
     */
    @Override
    public Result updateTimeParam(BaTimeParam baTimeParam) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baTimeParam.getId());
        try {
            Integer ret = this.baseMapper.update(baTimeParam, updateWrapper);
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
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result deleteTimeParam(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<BaTimeParam>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaTimeParam baTimeParam = new BaTimeParam();
            baTimeParam.setId(id);
            Integer ret=this.baseMapper.update(baTimeParam,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 启用
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        BaTimeParam baTimeParam =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baTimeParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaTimeParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaTimeParam baTimeParam =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baTimeParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 检查是否存在 param_code
     * @param code
     * @return
     */
    @Override
    public Result isExist(String code) {
        Result r = null;
        QueryWrapper<BaTimeParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("param_code",code);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.TIME_PARAM_CODE_1.getCode(),HttpResultEnum.TIME_PARAM_CODE_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.TIME_PARAM_CODE_0.getCode(),HttpResultEnum.TIME_PARAM_CODE_0.getMessage());
        }
        return r;
    }
}
