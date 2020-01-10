package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mchange.v2.beans.BeansUtils;
import com.settlement.bo.PageData;
import com.settlement.co.UserCo;
import com.settlement.config.RemoteProperties;
import com.settlement.entity.*;
import com.settlement.mapper.*;
import com.settlement.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import com.settlement.vo.SysUserVo;
import com.settlement.vo.UserStopVo;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Select;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

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
    @Autowired
    private SysUserStopMapper sysUserStopMapper;

    /**
     * 用户列表
     * @param userCo
     * @return
     */
    @Override
    public PageData getUserList(UserCo userCo) {
        userCo.setDelFlag(Const.DEL_FLAG_N);
        Page<SysUserVo> page = new Page<>(userCo.getPage(),userCo.getLimit());
        List<SysUserVo> sysUserVos = this.baseMapper.getUserList(userCo,page);
        page.setRecords(sysUserVos);
        return  new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 根据email 查询用户
     * @param email
     * @return
     */
    @Override
    public SysUser findUserByEmail(String email) {
        return this.baseMapper.getUserByEmail(email);
    }

    /**
     * 保存用户
     * @param userVo
     * @return
     */
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

    /**
     * 修改用户信息
     * @param userVo
     * @return
     */
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

    /**
     * 删除用户
     * @param id
     * @return
     */
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

    /**
     * 更新用户默认密码
     * @param id
     * @return
     */
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

    /**
     * 启用用户
     * @param id
     * @return
     */
    @Override
    public Result updateUserStart(Integer id) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<SysUser> updateWrapper = new UpdateWrapper<SysUser>();
        updateWrapper.set("enabled",Const.ENABLED_Y);
        updateWrapper.eq("id",id);
        SysUser user = new SysUser();
        user.setId(id);
        int ret = this.getBaseMapper().update(user,updateWrapper);
        if (ret > 0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }
    /**
     * 用户停用
     * @param userStopVo
     * @return
     */
    @Override
    public Result userStop(UserStopVo userStopVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            SysUser user = this.baseMapper.selectById(userStopVo.getUserId());
            user.setEnabled(Const.ENABLED_N);
            Integer ret = this.baseMapper.updateById(user);
            if(ret!=null && ret>0) {
                SysUserStop sysUserStop = new SysUserStop();
                sysUserStop.setUserId(userStopVo.getUserId());
                sysUserStop.setType(userStopVo.getStopType());
                sysUserStop.setCreateTime(new Date());
                sysUserStop.setRemark(userStopVo.getRemark());
                Integer ret2 = this.sysUserStopMapper.insert(sysUserStop);
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
     * 根据用户id 角色id 部门id获得用户列表
     * @param roleId
     * @param userId
     * @param deptId
     * @return
     */
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
    public List<SysUser> getAmListByDeptAndRole(Map<String, Object> map) {
        return this.baseMapper.getAmSelect(map);
    }

    /**
     * 根据deptId和roleCode获得当前部门下的AM角色的用户
     * @param deptId
     * @return
     */
    @Override
    public Result getUserByDeptIdAndRoleCode(Integer deptId) {
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("userId",user.getId());
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(map);
        List<SysUserVo> sysUserVos = new ArrayList<>();
        if(sysRole.getRoleCode().equals(Const.ROLE_CODE_AM)) {
            SysUserVo sysUserVo = new SysUserVo();
            sysUserVo.setId(user.getId());
            sysUserVo.setRealName(user.getRealName());
            sysUserVos.add(sysUserVo);
        } else {
            Map<String,Object> paramMap = new HashMap<String, Object>();
            paramMap.put("deptId",deptId);
            paramMap.put("roleCode",Const.ROLE_CODE_AM);
            paramMap.put("delFlag", Const.DEL_FLAG_N);
            sysUserVos = this.baseMapper.getUserByDeptIdAndRoleCode(paramMap);
        }

        if(sysUserVos!=null && sysUserVos.size()>0) {
            r.setData(sysUserVos);
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
        }
        return r;
    }



    /**
     * am 下拉列表
     * @param map
     * @return
     */
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

    /**
     * 助理下拉列表
     * @param map
     * @return
     */
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

    /**
     * 结算人员下拉列表
     * @param map
     * @return
     */
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
