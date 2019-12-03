package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.BaCustomer;
import com.settlement.entity.BaDeptCustomer;
import com.settlement.mapper.BaCustomerMapper;
import com.settlement.mapper.BaDeptCustomerMapper;
import com.settlement.service.BaCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.service.BaDeptCustomerService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaCustomerVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.management.BadAttributeValueExpException;
import java.util.Date;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Service
@Transactional
public class BaCustomerServiceImpl extends ServiceImpl<BaCustomerMapper, BaCustomer> implements BaCustomerService {
    @Autowired
    private BaDeptCustomerMapper baDeptCustomerMapper;

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
        try {
            baCustomerVo.setDelFlag(Const.DEL_FLAG_N);
            baCustomerVo.setEnabled(Const.ENABLED_Y);
            baCustomerVo.setCreateTime(new Date());
            Integer ret = this.baseMapper.insert(baCustomerVo);
            if (ret != null && ret > 0) {
                BaDeptCustomer baDeptCustomer = new BaDeptCustomer();
                baDeptCustomer.setDeptId(baCustomerVo.getDeptId());
                baDeptCustomer.setCustomerId(baCustomerVo.getId());
                Integer ret2 = baDeptCustomerMapper.insert(baDeptCustomer);
                if (ret2 != null && ret2 > 0) {
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
     * 修改
     * @param baCustomerVo
     * @return
     */
    @Override
    public Result update(BaCustomerVo baCustomerVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
        UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",baCustomerVo.getId());
        Integer ret=this.baseMapper.update(baCustomerVo,updateWrapper);
        if(ret!=null && ret>0) {
            QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_id",baCustomerVo.getId());
            BaDeptCustomer baDeptCustomer = baDeptCustomerMapper.selectOne(queryWrapper);
            baDeptCustomer.setDeptId(baCustomerVo.getDeptId());
            Integer ret2=baDeptCustomerMapper.updateById(baDeptCustomer);
            if(ret2!=null && ret2>0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
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
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<BaCustomer>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaCustomer baExportParam = this.baseMapper.selectById(id);
            Integer ret=this.baseMapper.update(baExportParam,updateWrapper);
            if(ret!=null && ret>0) {
                QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("customer_id",id);
                Integer ret2=baDeptCustomerMapper.delete(queryWrapper);
                if(ret2!=null && ret2>0) {
                    r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
                }
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
        try {
            UpdateWrapper<BaCustomer> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id", id);
            updateWrapper.set("enabled", Const.ENABLED_Y);
            BaCustomer baCustomer = this.baseMapper.selectById(id);

            Integer ret = this.baseMapper.update(baCustomer, updateWrapper);
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

    /**
     * 根据id查询得到BaCustomerVo
     * @param id
     * @return
     */
    @Override
    public BaCustomerVo getBaCustomerVoById(Integer id) {
        return this.baseMapper.getBaCustomerVoById(id);
    }
}
