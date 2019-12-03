package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.FormulaParamCo;
import com.settlement.entity.BaFormulaParam;
import com.settlement.mapper.BaFormulaParamMapper;
import com.settlement.service.BaFormulaParamService;
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
 * 结算公式参数表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Service
@Transactional
public class BaFormulaParamServiceImpl extends ServiceImpl<BaFormulaParamMapper, BaFormulaParam> implements BaFormulaParamService {

    /**
     * 加载列表页面
     * @param formulaParamCo
     * @return
     */
    @Override
    public PageData listPageData(FormulaParamCo formulaParamCo) {
        Page<BaFormulaParam> page = new Page<>(formulaParamCo.getPage(),formulaParamCo.getLimit());
        QueryWrapper<BaFormulaParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.like(StringUtils.isNotBlank(formulaParamCo.getKeyword()),"param_name",formulaParamCo.getKeyword());
        this.baseMapper.selectPage(page,queryWrapper);

        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     * @param baFormulaParam
     * @return
     */
    @Override
    public Result addFormulaParam(BaFormulaParam baFormulaParam) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        baFormulaParam.setDelFlag(Const.DEL_FLAG_N);
        baFormulaParam.setEnabled(Const.ENABLED_Y);
        baFormulaParam.setCreateTime(new Date());
        try {
            Integer ret = this.baseMapper.insert(baFormulaParam);
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
     * @param baFormulaParam
     * @return
     */
    @Override
    public Result updateFormulaParam(BaFormulaParam baFormulaParam) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaFormulaParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baFormulaParam.getId());
        try {
            Integer ret = this.baseMapper.update(baFormulaParam, updateWrapper);
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
    public Result deleteFormulaParam(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaFormulaParam> updateWrapper = new UpdateWrapper<BaFormulaParam>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaFormulaParam baFormulaParam = new BaFormulaParam();
            baFormulaParam.setId(id);
            Integer ret=this.baseMapper.update(baFormulaParam,updateWrapper);
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
     * 启用状态
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaFormulaParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        BaFormulaParam baFormulaParam =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baFormulaParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 停用状态
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaFormulaParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaFormulaParam baFormulaParam = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baFormulaParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }
    /**
     * 检查是否存在 col_value
     * @param code
     * @return
     */
    @Override
    public Result isExist(String code) {
        Result r = null;
        QueryWrapper<BaFormulaParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("param_code",code);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.FORMULA_PARAM_CODE_1.getCode(),HttpResultEnum.FORMULA_PARAM_CODE_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.FORMULA_PARAM_CODE_0.getCode(),HttpResultEnum.FORMULA_PARAM_CODE_0.getMessage());
        }
        return r;
    }
}
