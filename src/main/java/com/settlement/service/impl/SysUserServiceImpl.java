package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.UserCo;
import com.settlement.config.RemoteProperties;
import com.settlement.entity.SysUser;
import com.settlement.entity.SysUserRole;
import com.settlement.mapper.SysUserMapper;
import com.settlement.mapper.SysUserRoleMapper;
import com.settlement.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Service
@Transactional
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RemoteProperties remoteProperties;

    @Override
    public SysUser findUserByEmail(String email) {
        return this.baseMapper.getUserByEmail(email);
    }

    @Override
    public Result saveUser(SysUserVo userVo) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            Integer ret = sysUserMapper.insert(userVo);
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userVo.getId());
            userRole.setRoleId(userVo.getRole().getId());
            Integer ret1 = sysUserRoleMapper.insert(userRole);
            if ((ret != null && ret > 0) && (ret1 != null && ret1 > 0)) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    @Override
    public Result updateUser(SysUserVo userVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
            updateWrapper.set("real_name", userVo.getRealName());
            updateWrapper.set("employee_no", userVo.getEmployeeNo());
            updateWrapper.set("mobile", userVo.getMobile());
            updateWrapper.set("dept_id", userVo.getDeptId());
            updateWrapper.eq("id", userVo.getId());
            Integer ret = sysUserMapper.update(userVo, updateWrapper);
            // 删除用户角色关联表
            Integer delRet = sysUserRoleMapper.delete(new QueryWrapper<SysUserRole>().eq("user_id", userVo.getId()));
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userVo.getId());
            userRole.setRoleId(userVo.getRole().getId());
            Integer ret1 = sysUserRoleMapper.insert(userRole);
            if ((ret != null && ret > 0) && (ret1 != null && ret1 > 0) && (delRet != null && delRet > 0)) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    @Override
    public Result deleteUser(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            SysUser user = new SysUser();
            user.setId(id);
            Integer ret = sysUserMapper.update(user,updateWrapper);
            if ( ret != null && ret > 0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result updateUserDefaultPassword(Integer id) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(),HttpResultEnum.CODE_500.getMessage());
        // 读取系统默认密码
        String defaultPass = remoteProperties.getDefaultPass();
        if (defaultPass != null && !"".equals(defaultPass)) {
            UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
            updateWrapper.set("password",defaultPass);
            updateWrapper.eq("id",id);
            SysUser user = new SysUser();
            user.setId(id);
            int ret = this.getBaseMapper().update(user,updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.CODE_200.getCode());
                r.setMsg(HttpResultEnum.CODE_200.getMessage());
            }
        }
        return r;
    }

    @Override
    public Result updateUserStart(Integer id) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(),HttpResultEnum.CODE_500.getMessage());
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
        updateWrapper.set("enabled",Const.ENABLED_Y);
        updateWrapper.eq("id",id);
        SysUser user = new SysUser();
        user.setId(id);
        int ret = this.getBaseMapper().update(user,updateWrapper);
        if (ret > 0) {
            r.setCode(HttpResultEnum.CODE_200.getCode());
            r.setMsg(HttpResultEnum.CODE_200.getMessage());
        }
        return r;
    }
}
