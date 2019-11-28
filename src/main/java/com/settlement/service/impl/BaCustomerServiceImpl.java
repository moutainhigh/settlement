package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.BaCustomer;
import com.settlement.mapper.BaCustomerMapper;
import com.settlement.service.BaCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaCustomerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
@Service
public class BaCustomerServiceImpl extends ServiceImpl<BaCustomerMapper, BaCustomer> implements BaCustomerService {
    /**
     * 加载列表页面
     * @param customerCo
     * @return
     */
    @Override
    public PageData listPageData(CustomerCo customerCo) {
        Page<BaCustomer> page = new Page<>(customerCo.getPage(),customerCo.getLimit());
        QueryWrapper<BaCustomer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
        queryWrapper.like(StringUtils.isNotBlank(customerCo.getKeyword()),"param_name",customerCo.getKeyword());
        this.baseMapper.selectPage(page,queryWrapper);

        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result add(BaCustomerVo baCustomerVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        baCustomerVo.setDelFlag(Const.DEL_FLAG_N);
        baCustomerVo.setEnabled(Const.ENABLED_Y);
        baCustomerVo.setCreateTime(new Date());
        Integer ret = this.baseMapper.insert(baCustomerVo);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
            r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
        }
        return  r;

    }

    /**
     * 修改
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result update(BaCustomerVo baCustomerVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baCustomerVo.getId());
        Integer ret=this.baseMapper.update(baCustomerVo,updateWrapper);
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
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<BaCustomer>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaCustomer baExportParam = this.baseMapper.selectById(id);
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
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        BaCustomer baCustomer =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baCustomer,updateWrapper);
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
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        BaCustomer baCustomer = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(baCustomer,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }
}
