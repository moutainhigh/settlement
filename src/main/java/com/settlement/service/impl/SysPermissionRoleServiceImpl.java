package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.entity.SysPermissionRole;
import com.settlement.mapper.SysPermissionRoleMapper;
import com.settlement.service.SysPermissionRoleService;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysRoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 菜单角色关联表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-26
 */
@Service
public class SysPermissionRoleServiceImpl extends ServiceImpl<SysPermissionRoleMapper, SysPermissionRole> implements SysPermissionRoleService {

    @Autowired
    private SysPermissionRoleMapper sysPermissionRoleMapper;
    @Override
    public Result savePermissionRoles(SysRoleVo sysRoleVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        List<SysPermissionRole> sysPermissionRoles = new ArrayList<>();
        try{
            //删除
            QueryWrapper<SysPermissionRole> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",sysRoleVo.getId());
            this.remove(queryWrapper);
            //添加
            for(Integer id: sysRoleVo.getSysPermissions()) {
                if(id!=null) {
                    SysPermissionRole sysPermissionRole = new SysPermissionRole();
                    sysPermissionRole.setPermissionId(id);
                    sysPermissionRole.setRoleId(sysRoleVo.getId());
                    sysPermissionRoles.add(sysPermissionRole);
                }
            }
            boolean ret=this.saveBatch(sysPermissionRoles);
            if(ret) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }
}
