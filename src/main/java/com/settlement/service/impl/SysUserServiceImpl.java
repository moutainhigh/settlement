package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.co.UserCo;
import com.settlement.config.RemoteProperties;
import com.settlement.entity.SysDept;
import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import com.settlement.entity.SysUserRole;
import com.settlement.mapper.SysDeptMapper;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.mapper.SysUserMapper;
import com.settlement.mapper.SysUserRoleMapper;
import com.settlement.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import com.settlement.vo.SysUserVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysDeptMapper sysDeptMapper;

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

    @Override
    public Result getUserSelectByRoleDept(Integer roleId, Integer userId, Integer deptId) {
        List<SysUser> users = sysUserMapper.getUserSelectByRoleDept(roleId, userId, deptId);
        List<SelectVo> userSelect = new ArrayList<SelectVo>();
        if (users != null) {
            for (SysUser user : users) {
                SelectVo sv = new SelectVo(user.getId(), user.getRealName());
                userSelect.add(sv);
            }
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(userSelect);
        return r;
    }

    @Override
    public SysUserVo getUserStop(Integer id) {
        SysUser user = this.getBaseMapper().selectOne(new QueryWrapper<SysUser>().eq("id", id));
        SysUserRole userRole = sysUserRoleMapper.selectOne(new QueryWrapper<SysUserRole>().eq("user_id",id));
        SysRole role = sysRoleMapper.selectOne(new QueryWrapper<SysRole>().eq("id",userRole.getRoleId()));
        SysDept dept = sysDeptMapper.selectOne(new QueryWrapper<SysDept>().eq("id",user.getDeptId()));
        SysUserVo userVo = new SysUserVo();
        userVo.setId(user.getId());
        userVo.setRealName(user.getRealName());
        userVo.setDeptId(user.getDeptId());
        userVo.setEmployeeNo(user.getEmployeeNo());
        userVo.setRole(role);
        userVo.setDeptName(dept.getDeptName());
        return userVo;
    }

    @Override
    public Result getAmSelect(Map<String, Object> map) {
        List<SysUser> amList = this.baseMapper.getAmSelect(map);
        List<SelectVo> amSelectList = new ArrayList<SelectVo>();
        if (amList != null && amList.size() > 0) {
            SelectVo selectVo = null;
            for (SysUser am : amList) {
                selectVo = new SelectVo(am.getId(), am.getRealName());
                amSelectList.add(selectVo);
            }
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(amSelectList);
        return r;
    }

    @Override
    public Result getAssistantSelect(Map<String, Object> map) {
        List<SysUser> assistantList = this.baseMapper.getAssistantSelect(map);
        List<SelectVo> assistantSelectList = new ArrayList<SelectVo>();
        if (assistantList != null && assistantList.size() > 0) {
            SelectVo selectVo = null;
            for (SysUser assistant : assistantList) {
                selectVo = new SelectVo(assistant.getId(), assistant.getRealName());
                assistantSelectList.add(selectVo);
            }
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(assistantSelectList);
        return r;
    }

    @Override
    public Result getSettlementSelect(Map<String, Object> map) {
        List<SysUser> settlementList = this.baseMapper.getSettlementSelect(map);
        List<SelectVo> settlementSelectList = new ArrayList<SelectVo>();
        if (settlementList != null && settlementList.size() > 0) {
            SelectVo selectVo = null;
            for (SysUser assistant : settlementList) {
                selectVo = new SelectVo(assistant.getId(), assistant.getRealName());
                settlementSelectList.add(selectVo);
            }
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(settlementSelectList);
        return r;
    }
}
