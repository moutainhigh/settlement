package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.SysDeptRole;
import com.settlement.mapper.SysDeptRoleMapper;
import com.settlement.service.SysDeptRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysDeptVo;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-12-01
 */
@Service
@Transactional
public class SysDeptRoleServiceImpl extends ServiceImpl<SysDeptRoleMapper, SysDeptRole> implements SysDeptRoleService {
    /**
     * 添加部门角色
     * @param sysDeptVo
     * @return
     */
    @Override
    public Result add(SysDeptVo sysDeptVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try{
            //删除
            QueryWrapper<SysDeptRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dept_id",sysDeptVo.getId());
            this.baseMapper.delete(queryWrapper);

            List<Integer> roleIds = sysDeptVo.getRoles();
            List<SysDeptRole> sysDeptRoles = new ArrayList<>();
            for(Integer roleId : roleIds) {
                SysDeptRole sysDeptRole = new SysDeptRole();
                sysDeptRole.setRoleId(roleId);
                sysDeptRole.setDeptId(sysDeptVo.getId());
                sysDeptRoles.add(sysDeptRole);
            }
            boolean ret = this.saveBatch(sysDeptRoles);
            if(ret) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 修改
     * @param sysDeptVo
     * @return
     */
    @Override
    public Result update(SysDeptVo sysDeptVo) {
        Result r =  new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            //删除
            QueryWrapper<SysDeptRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("dept_id",sysDeptVo.getId());
            this.baseMapper.delete(queryWrapper);

            List<Integer> roleIds = sysDeptVo.getRoles();
            List<SysDeptRole> sysDeptRoles = new ArrayList<>();
            for(Integer roleId : roleIds) {
                SysDeptRole sysDeptRole = new SysDeptRole();
                sysDeptRole.setRoleId(roleId);
                sysDeptRole.setDeptId(sysDeptVo.getId());
                sysDeptRoles.add(sysDeptRole);
            }
            boolean ret = this.saveBatch(sysDeptRoles);
            if(ret) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        }catch (Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return r;
    }

    /**
     * 删除
     * @param deptId
     * @return
     */
    @Override
    public Result delete(Integer deptId) {
        Result r =  new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        //删除
        QueryWrapper<SysDeptRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_id",deptId);
        try {
            Integer ret = this.baseMapper.delete(queryWrapper);
            if (ret != null && ret > 0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
