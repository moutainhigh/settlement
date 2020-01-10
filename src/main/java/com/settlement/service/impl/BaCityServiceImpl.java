package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.CityCo;
import com.settlement.entity.BaCity;
import com.settlement.mapper.BaCityMapper;
import com.settlement.service.BaCityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.coyote.http11.Http11AprProtocol;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
@Service
public class BaCityServiceImpl extends ServiceImpl<BaCityMapper, BaCity> implements BaCityService {
    @Autowired
    private BaCityMapper baCityMapper;

    /**
     * 加载列表页面
     *
     * @param cityCo
     * @return
     */
    @Override
    public PageData listPageData(CityCo cityCo) {
        QueryWrapper<BaCity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.eq("enabled", Const.ENABLED_Y);
        queryWrapper.like(StringUtils.isNotBlank(cityCo.getKeyword()), "city_name", cityCo.getKeyword());
        Page<BaCity> page = new Page<>(cityCo.getPage(), cityCo.getLimit());
        page.setRecords(this.baseMapper.selectList(queryWrapper));
        return new PageData(page.getTotal(), page.getRecords());
    }

    /**
     * 添加
     *
     * @param baCity
     * @return
     */
    @Override
    public Result add(BaCity baCity) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            baCity.setDelFlag(Const.DEL_FLAG_N);
            baCity.setEnabled(Const.ENABLED_Y);
            baCity.setCreateTime(new Date());
            Integer ret = this.baseMapper.insert(baCity);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;

    }

    /**
     * 修改
     *
     * @param baCity
     * @return
     */
    @Override
    public Result update(BaCity baCity) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaCity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", baCity.getId());
            Integer ret = this.baseMapper.update(baCity, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Override
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(), HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<BaCity> updateWrapper = new UpdateWrapper<BaCity>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id", id);
            BaCity baCity = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(baCity, updateWrapper);
            if (ret != null && ret > 0) {
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
     *
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaCity> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("enabled", Const.ENABLED_Y);
            BaCity baCity = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(baCity, updateWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 停用状态
     *
     * @param id
     * @return
     */
    @Override
    public Result updateEnableStop(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(), HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaCity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaCity baCity = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baCity, updateWrapper);
        if (ret != null && ret > 0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }
    /**
     * 获得城市列表
     * @return
     */
    @Override
    public Result getBaCityListAjax() {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        List<BaCity> baCities = getBaCityList();
        List<SelectVo> cityLists = new ArrayList<SelectVo>();
        if(baCities!=null && baCities.size()>0) {
            for (BaCity city : baCities) {
                SelectVo selectVo  = new SelectVo();
                selectVo.setValue(city.getId());
                selectVo.setName(city.getCityName());
                cityLists.add(selectVo);
            }
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setData(cityLists);
        }
        return r;
    }
    /**
     * 获得城市列表
     * @return
     */
    @Override
    public List<BaCity> getBaCityList() {
        QueryWrapper<BaCity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.eq("enabled", Const.ENABLED_Y);
        List<BaCity> baCities = this.baCityMapper.selectList(queryWrapper);
        return baCities;
    }

}


