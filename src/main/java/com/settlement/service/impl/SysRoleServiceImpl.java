package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.RoleCo;
import com.settlement.entity.SysRole;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.service.SysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SysRoleVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.awt.geom.QuadCurve2D;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    /**
     * 加载显示角色列表
     * @param roleCo
     * @return
     */
    @Override
    public PageData listPageData(RoleCo roleCo) {
        IPage<SysRole> page = new Page<>(roleCo.getPage(),roleCo.getLimit());
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(roleCo.getKeyword()),"role_cn_name",roleCo.getKeyword());
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        this.baseMapper.selectPage(page,queryWrapper);
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 所有的角色
     * @return
     */
    @Override
    public List<SysRoleVo> getRoleVo() {
        return this.baseMapper.getRoleVo();
    }

    /**
     * 根据部门id获得对应的选中的角色状态
     * @param deptId
     * @return
     */
    @Override
    public List<SysRoleVo> getRoleVoByDeptId(Integer deptId) {
        List<SysRoleVo> sysRoleVos = this.baseMapper.getRoleVo();
        List<SysRoleVo> sysRoleCheckedVos = this.baseMapper.getRoleVoByDeptId(deptId);
        for(SysRoleVo sysCheckedRoleVo:sysRoleCheckedVos) {
            for(SysRoleVo sysRoleVo:sysRoleVos) {
                if(sysCheckedRoleVo.getId().equals(sysRoleVo.getId())){
                    sysRoleVo.setChecked(true);
                    break;
                }
            }
        }
        return sysRoleVos;
    }

    /**
     * 根据用户id 查询角色信息
     * @param userId
     * @return
     */
    @Override
    public List<SysRole> findRoleByUserId(Integer userId) {
        return this.baseMapper.getSysRoleByUserId(userId);
    }

    @Override
    public Result getRolesByDeptId(Integer deptId) {
        Result r = new Result(HttpResultEnum.CODE_200.getCode(), HttpResultEnum.CODE_200.getMessage());
        r.setData(this.baseMapper.getRolesByDeptId(deptId));
        return r;
    }

    /**
     * 保存角色
     * @param sysRoleVo
     * @return
     */
    @Override
    public Result saveRole(SysRoleVo sysRoleVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        SysRole sysRole = new SysRole();
        sysRole.setRoleCnName(sysRoleVo.getRoleCnName());
        sysRole.setRoleEnName(sysRoleVo.getRoleEnName());
        sysRole.setRoleCode(sysRoleVo.getRoleCode());
        sysRole.setRemark(sysRoleVo.getRemark());
        sysRole.setCreateUserId(sysRoleVo.getCreateUserId());
        sysRole.setCreateTime(new Date());
        sysRole.setDelFlag(Const.DEL_FLAG_N);
        try {
            Integer ret = this.baseMapper.insert(sysRole);
            if (ret != null & ret > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                r.setData(sysRole);
            }
        }catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return  r;
    }

    /**
     *  更新角色内容
     * @param sysRoleVo
     * @return
     */
    @Override
    public Result updateRole(SysRoleVo sysRoleVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        SysRole sysRole = new SysRole();
        sysRole.setRoleCnName(sysRoleVo.getRoleCnName());
        sysRole.setRoleEnName(sysRoleVo.getRoleEnName());
        sysRole.setRoleCode(sysRoleVo.getRoleCode());
        sysRole.setRemark(sysRoleVo.getRemark());
        sysRole.setDelFlag(Const.DEL_FLAG_N);
        UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",sysRoleVo.getId());
        try {
            Integer ret = this.baseMapper.update(sysRole, updateWrapper);
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
     * 删除角色
     * @param id
     * @return
     */
    @Override
    public Result deleteRole(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(),HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<SysRole> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id", id);
            SysRole sysRole = this.baseMapper.selectById(id);
            Integer ret = this.baseMapper.update(sysRole, updateWrapper);
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

    /**
     * 检查角色role_code是否存在
     * @param roleCode
     * @return
     */
    @Override
    public Result roleCodeIsExist(String roleCode) {
        Result r = new Result();
        QueryWrapper<SysRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_code",roleCode);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count=this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0){
            r.setCode(HttpResultEnum.ROLE_CODE_1.getCode());
            r.setMsg(HttpResultEnum.ROLE_CODE_1.getMessage());
        } else{
            r.setCode(HttpResultEnum.ROLE_CODE_0.getCode());
            r.setMsg(HttpResultEnum.ROLE_CODE_0.getMessage());

        }
        return r;
    }


}
