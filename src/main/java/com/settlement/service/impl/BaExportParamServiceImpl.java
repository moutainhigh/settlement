package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ExportParamCo;
import com.settlement.entity.BaExportParam;
import com.settlement.mapper.BaExportParamMapper;
import com.settlement.service.BaExportParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * <p>
 * 导出参数表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-27
 */
@Service
public class BaExportParamServiceImpl extends ServiceImpl<BaExportParamMapper, BaExportParam> implements BaExportParamService {
    /**
     * 加载列表页面
     * @param exportParamCo
     * @return
     */
    @Override
    public PageData listPageData(ExportParamCo exportParamCo) {
        Page<BaExportParam> page = new Page<>(exportParamCo.getPage(),exportParamCo.getLimit());
        QueryWrapper<BaExportParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.like(StringUtils.isNotBlank(exportParamCo.getKeyword()),"param_name",exportParamCo.getKeyword());
        this.baseMapper.selectPage(page,queryWrapper);

        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     * @param baExportParam
     * @return
     */
    @Override
    public Result addExportParam(BaExportParam baExportParam) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        baExportParam.setDelFlag(Const.DEL_FLAG_N);
        baExportParam.setEnabled(Const.ENABLED_Y);
        baExportParam.setCreateTime(new Date());
        Integer ret = this.baseMapper.insert(baExportParam);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
            r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
        }
        return  r;

    }

    /**
     * 修改
     * @param baExportParam
     * @return
     */
    @Override
    public Result updateExportParam(BaExportParam baExportParam) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaExportParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baExportParam.getId());
        Integer ret=this.baseMapper.update(baExportParam,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result deleteExportParam(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaExportParam> updateWrapper = new UpdateWrapper<BaExportParam>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaExportParam baExportParam = new BaExportParam();
            baExportParam.setId(id);
            Integer ret=this.baseMapper.update(baExportParam,updateWrapper);
            if(ret!=null && ret>0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();

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
        UpdateWrapper<BaExportParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        BaExportParam baExportParam =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baExportParam,updateWrapper);
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
        UpdateWrapper<BaExportParam> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaExportParam baExportParam = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baExportParam,updateWrapper);
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
        QueryWrapper<BaExportParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("col_value",code);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.EXPORT_COL_VALUE_1.getCode(),HttpResultEnum.EXPORT_COL_VALUE_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.EXPORT_COL_VALUE_0.getCode(),HttpResultEnum.EXPORT_COL_VALUE_0.getMessage());
        }
        return r;
    }
}
