package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.entity.BaCustomer;
import com.settlement.entity.BaDeptCustomer;
import com.settlement.mapper.BaDeptCustomerMapper;
import com.settlement.service.BaDeptCustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaCustomerVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@Service
@Transactional
public class BaDeptCustomerServiceImpl extends ServiceImpl<BaDeptCustomerMapper, BaDeptCustomer> implements BaDeptCustomerService {

    /**
     * 添加
     * @param baDeptCustomer
     * @return
     */
    @Override
    public Result add(BaDeptCustomer baDeptCustomer) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            Integer ret = this.baseMapper.insert(baDeptCustomer);
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
     * @param baDeptCustomer
     * @return
     */
    @Override
    public Result update(BaDeptCustomer baDeptCustomer) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<BaDeptCustomer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("customer_id",baDeptCustomer.getCustomerId());
        try {
            Integer ret = this.baseMapper.update(baDeptCustomer, updateWrapper);
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
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            QueryWrapper<BaDeptCustomer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("customer_id",id);
            Integer ret=this.baseMapper.delete(queryWrapper);
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
}
