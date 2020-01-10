package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ApplyTransferCo;
import com.settlement.entity.*;
import com.settlement.mapper.*;
import com.settlement.service.BaApplyTransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.ApplyTransferCheckVo;
import com.settlement.vo.BaApplyTransferVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import java.util.*;

/**
 * <p>
 * 申请移交项目和客户 服务实现类
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
@Service
@Transactional
public class BaApplyTransferServiceImpl extends ServiceImpl<BaApplyTransferMapper, BaApplyTransfer> implements BaApplyTransferService {

    @Autowired
    private BaApplyTransferMapper baApplyTransferMapper;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private BaTransferMapper baTransferMapper;
    @Autowired
    private BaCustomerMapper baCustomerMapper;
    @Autowired
    private BaProjectGroupMapper baProjectGroupMapper;
    /**
     * 移交审核列表
     * @param applyTransferCo
     * @return
     */
    @Override
    public PageData listPage(ApplyTransferCo applyTransferCo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal() ;
        Map<String,Object> map = new HashMap<>();
        map.put("userId",user.getId());
        map.put("delFlag", Const.DEL_FLAG_N);
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(map);
        applyTransferCo.setRoleCode(sysRole.getRoleCode());
        if(sysRole.getRoleCode().equals(Const.ROLE_CODE_AM)) {
            applyTransferCo.setApplyType(Const.APPLY_TRANSFER_PROJECT);
            applyTransferCo.setCheckUser(user.getId());
        }
        Page<BaApplyTransferVo> page = new Page<>(applyTransferCo.getPage(),applyTransferCo.getLimit());
        List<BaApplyTransferVo> baApplyTransferVos = this.baseMapper.listPage(applyTransferCo,page);
        page.setRecords(baApplyTransferVos);
        return  new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 移交审核-客户详情 transferCustomerDetails
     * 移交审核-项目详情 transferProjectDetails
     * @param applyTransferCo
     * @return
     */
    @Override
    public PageData detailListPage(ApplyTransferCo applyTransferCo) {
        Page<BaApplyTransferVo> page = new Page<>(applyTransferCo.getPage(),applyTransferCo.getLimit());
        BaApplyTransfer baApplyTransfer = this.baApplyTransferMapper.selectById(applyTransferCo.getApplyId());
        if(baApplyTransfer.getApplyType().equals(Const.APPLY_TRANSFER_CUSTOMER)){
           List<BaApplyTransferVo> baApplyTransferVos = this.baseMapper.transferCustomerDetails(baApplyTransfer,page);
            page.setRecords(baApplyTransferVos);
        } else if(baApplyTransfer.getApplyType().equals(Const.APPLY_TRANSFER_PROJECT)) {
            List<BaApplyTransferVo> baApplyTransferVos = this.baseMapper.transferProjectDetails(baApplyTransfer,page);
            page.setRecords(baApplyTransferVos);
        }
        return  new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 审核移交操作
     * @param applyTransferCheckVo
     * @return
     */
    @Override
    public Result checkApply(ApplyTransferCheckVo applyTransferCheckVo) {
        Result r = new Result(HttpResultEnum.CHK_CODE_500.getCode(),HttpResultEnum.CHK_CODE_500.getMessage());
        try {
            //根据applyId查询当前需要移交的数据
            QueryWrapper<BaTransfer> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("apply_transfer_id", applyTransferCheckVo.getApplyId());
            List<BaTransfer> baTransfers = baTransferMapper.selectList(queryWrapper);
            //更新当前申请的状态和时间
            BaApplyTransfer baApplyTransfer = this.baApplyTransferMapper.selectById(applyTransferCheckVo.getApplyId());
            baApplyTransfer.setCheckStatus(applyTransferCheckVo.getCheckResult());
            baApplyTransfer.setCheckTime(new Date());
            if(Const.CHECK_RESULT_PASS_CODE.equals(applyTransferCheckVo.getCheckResult())){
                baApplyTransfer.setCheckStatus(Const.CHECK_STATUS_CHECK_PASS);
            } else if(Const.CHECK_RESULT_NOPASS_CODE.equals(applyTransferCheckVo.getCheckResult())){
                baApplyTransfer.setCheckStatus(Const.CHECK_STATUS_CHECK_NOPASS);
            }
            Integer ret = this.baApplyTransferMapper.updateById(baApplyTransfer);
            if(baApplyTransfer.getCheckStatus().equals(Const.CHECK_STATUS_CHECK_PASS)) {
                if (ret != null && ret > 0) {
                    Integer ret2 = null;
                    //更新对应的移交数据的人员
                    if (baApplyTransfer.getApplyType().equals(Const.APPLY_TRANSFER_CUSTOMER)) {
                        List<BaCustomer> baCustomers = new ArrayList<>();
                        for (BaTransfer baTransfer : baTransfers) {
                            BaCustomer baCustomer = baCustomerMapper.selectById(baTransfer.getTransferId());
                            baCustomer.setChief(baApplyTransfer.getRecieveUser());
                            baCustomers.add(baCustomer);
                        }
                        ret2 = baCustomerMapper.updateBatchs(baCustomers);
                    } else if (baApplyTransfer.getApplyType().equals(Const.APPLY_TRANSFER_PROJECT)) {
                        List<BaProjectGroup> baProjectGroups = new ArrayList<>();
                        for (BaTransfer baTransfer : baTransfers) {
                            BaProjectGroup baProjectGroup = baProjectGroupMapper.selectById(baTransfer.getTransferId());
                            baProjectGroup.setOwnerUserId(baApplyTransfer.getRecieveUser());
                            baProjectGroups.add(baProjectGroup);
                        }
                        ret2 = baProjectGroupMapper.updateBatchs(baProjectGroups);
                    }
                    if (ret2 != null && ret2 > 0) {
                        r.setCode(HttpResultEnum.CHK_CODE_200.getCode());
                        r.setMsg(HttpResultEnum.CHK_CODE_200.getMessage());
                    }
                }
            } else {
                if (ret != null && ret > 0) {
                    r.setCode(HttpResultEnum.CHK_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CHK_CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    /**
     * 检查是否存在审核移交未审核的数据
     * @param ids
     * @return
     */
    @Override
    public Result checkApplyStatus(Integer[] ids) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        QueryWrapper<BaApplyTransfer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("check_status",Const.CHECK_STATUS_NO_CHECK);
        queryWrapper.in("id",ids);
        List<BaApplyTransfer> baApplyTransfers = this.baApplyTransferMapper.selectList(queryWrapper);
        if(baApplyTransfers!=null && baApplyTransfers.size()>0) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(baApplyTransfers);
        }
        return r;
    }
}
