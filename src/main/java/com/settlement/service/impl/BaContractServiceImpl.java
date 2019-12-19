package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ContractCo;
import com.settlement.entity.BaContract;
import com.settlement.mapper.BaContractMapper;
import com.settlement.service.BaContractService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.BaContractVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 合同管理表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@Service
@Transactional
public class BaContractServiceImpl extends ServiceImpl<BaContractMapper, BaContract> implements BaContractService {
    /**
     * 加载列表页面
     * @param contractCo
     * @return
     */
    @Override
    public PageData listPageData(ContractCo contractCo) {
        contractCo.setDelFlag(Const.DEL_FLAG_N);
        Page<BaContractVo> page = new Page<>(contractCo.getPage(),contractCo.getLimit());
        page.setRecords(this.baseMapper.getBaContractVos(contractCo,page));
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     * @param baContractVo
     * @return
     */
    @Override
    public Result add(BaContractVo baContractVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            baContractVo.setDelFlag(Const.DEL_FLAG_N);
            baContractVo.setStatus(Const.CONTRACT_STATUS_Y);
            baContractVo.setCreateTime(new Date());
            Integer ret = this.baseMapper.insert(baContractVo);
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
     * @param baContractVo
     * @return
     */
    @Override
    public Result update(BaContractVo baContractVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            UpdateWrapper<BaContract> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",baContractVo.getId());
            Integer ret=this.baseMapper.update(baContractVo,updateWrapper);
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
     * 删除
     * @param id
     * @return
     */
    @Override
    public Result delete(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try{
            UpdateWrapper<BaContract> updateWrapper = new UpdateWrapper<BaContract>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            BaContract baContract = this.baseMapper.selectById(id);
            Integer ret=this.baseMapper.update(baContract,updateWrapper);
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
     * 根据id获得BaContractVo
     * @param id
     * @return
     */
    @Override
    public BaContractVo getBaContractVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("id",id);
        return this.baseMapper.getBaContractVoById(map);
    }

    /**
     * 检查是否存在 col_value
     * @param contractNo
     * @return
     */
    @Override
    public Result isExist(String contractNo) {
        Result r = null;
        QueryWrapper<BaContract> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("contract_no",contractNo);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.CONTRACT_NO_1.getCode(),HttpResultEnum.CONTRACT_NO_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.CONTRACT_NO_0.getCode(),HttpResultEnum.CONTRACT_NO_0.getMessage());
        }
        return r;
    }
}
