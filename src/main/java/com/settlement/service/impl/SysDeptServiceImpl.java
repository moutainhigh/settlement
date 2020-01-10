package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.entity.SysDept;
import com.settlement.entity.SysRole;
import com.settlement.entity.SysUser;
import com.settlement.mapper.SysDeptMapper;
import com.settlement.mapper.SysRoleMapper;
import com.settlement.service.SysDeptRoleService;
import com.settlement.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import com.settlement.vo.SysDeptRoleUserVo;
import com.settlement.vo.SysDeptVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@Service
@Transactional
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Autowired
    private SysDeptRoleService sysDeptRoleService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    /**
     * 构造部门下拉列表
     * @return
     */
    @Override
    public Result getDeptSelect() {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Map<String,Object> map = new HashMap<>();
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("userId",user.getId());
        SysRole sysRole = sysRoleMapper.getSysRoleByUserId(map);
        List<SelectVo> deptSelectVoList = new ArrayList<SelectVo>();
        if(sysRole.getRoleCode().equals(Const.ROLE_CODE_AM)) {
            SysDept sysDept = this.baseMapper.selectById(user.getDeptId());
            SelectVo selectVo = new SelectVo();
            selectVo.setValue(sysDept.getId());
            selectVo.setName(sysDept.getDeptName());
            deptSelectVoList.add(selectVo);
        } else if(sysRole.getRoleCode().equals(Const.ROLE_CODE_ADMIN)){
            QueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>();
            queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
            queryWrapper.ne("dept_code", Const.DEPT_ROOT);
            queryWrapper.orderByAsc("sort");
            List<SysDept> deptList =  this.baseMapper.selectList(queryWrapper);
            if (deptList != null && deptList.size() > 0) {
                deptSelectVoList = buildDeptTree(deptList);
            }
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(deptSelectVoList);
        return r;
    }

    /**
     * @description 构建部门下拉框树形结构
     *
     * @auth admin
     * @date 2019-11-29
     * @param list
     * @return
     */
    private List<SelectVo> buildDeptTree(List<SysDept> list) {
        List<SelectVo> deptTreeList = new ArrayList<SelectVo>();
        SelectVo selectVo = null;
        for (SysDept dept : getLevelOneDept(list)) {
            selectVo = new SelectVo();
            selectVo.setValue(dept.getId());
            selectVo.setName(dept.getDeptName());
            buildChildTree(selectVo, dept.getId(), list);
            deptTreeList.add(selectVo);
        }
        return deptTreeList;
    }

    /**
     * @description 构建子节点树形结构
     *
     * @auth admin
     * @date 2019-11-29
     * @param sv
     * @param id
     * @return
     */
    private SelectVo buildChildTree(SelectVo sv, Integer id, List<SysDept> list) {
        List<SelectVo> childSelectVo = new ArrayList<SelectVo>();
        SelectVo svo = null;
        for (SysDept dept : list) {
            if (dept.getParentId() == id) {
                svo = new SelectVo();
                svo.setValue(dept.getId());
                svo.setName(dept.getDeptName());
                childSelectVo.add(buildChildTree(svo, dept.getId(), list));
            }
        }
        sv.setChildren(childSelectVo);
        return sv;
    }

    /**
     * @description 获取一级部门
     *
     * @auth admin
     * @date 2019-11-29
     * @param list
     * @return
     */
    private List<SysDept> getLevelOneDept(List<SysDept> list) {
        List<SysDept> levelOneList = new ArrayList<SysDept>();
        for (SysDept dept : list)  {
            if (dept.getParentId() == Const.DEPT_ROOT_ID) {
                levelOneList.add(dept);
            }
        }
        return levelOneList;
    }

    /**
     * 加载部门树列表
     * @return
     */
    @Override
    public List<SysDeptVo> getDeptTreeList() {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("enabled",Const.ENABLED_Y);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        List<SysDept> sysDepts = this.baseMapper.selectList(queryWrapper);

        List<SysDeptVo> sysDeptVos = new ArrayList<>();
        for(SysDept sysDept:sysDepts) {
            SysDeptVo sysDeptVo = new SysDeptVo();
            sysDeptVo.setId(sysDept.getId());
            sysDeptVo.setEnabled(sysDept.getEnabled());
            sysDeptVo.setDeptName(sysDept.getDeptName());
            sysDeptVo.setDeptCode(sysDept.getDeptCode());
            sysDeptVo.setChief(sysDept.getChief());
            sysDeptVo.setEmail(sysDept.getEmail());
            sysDeptVo.setCheckArr("0");
            sysDeptVo.setTitle(sysDept.getDeptName());
            sysDeptVo.setChecked(false);
            sysDeptVo.setParentId(sysDept.getParentId());

            sysDeptVos.add(sysDeptVo);
        }
        return sysDeptVos;
    }
    /**
     * 添加
     * @param sysDeptVo
     * @return
     */
    @Override
    public Result add(SysDeptVo sysDeptVo) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        try{
            SysDept sysDept = new SysDept();
            sysDept.setDeptCode(sysDeptVo.getDeptCode());
            sysDept.setDeptName(sysDeptVo.getDeptName());
            sysDept.setChief(sysDeptVo.getChief());
            sysDept.setEmail(sysDeptVo.getEmail());
            sysDept.setParentId(sysDeptVo.getParentId());
            sysDept.setSort(sysDeptVo.getSort());
            sysDept.setEnabled(Const.ENABLED_Y);
            sysDept.setDelFlag(Const.DEL_FLAG_N);
            sysDept.setRemark(sysDeptVo.getRemark());
            sysDept.setCreateTime(new Date());
            Integer ret = this.baseMapper.insert(sysDept);
            if(ret!=null && ret>0) {
                sysDeptVo.setId(sysDept.getId());
                Result r1=sysDeptRoleService.add(sysDeptVo);
                if(r1.getCode().equals(HttpResultEnum.ADD_CODE_200.getCode())) {
                    r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                    r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }

        return  r;

    }

    /**
     * 修改
     * @param sysDeptVo
     * @return
     */
    @Override
    public Result update(SysDeptVo sysDeptVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try{
            SysDept sysDept = new SysDept();
            sysDept.setId(sysDeptVo.getId());
            sysDept.setDeptCode(sysDeptVo.getDeptCode());
            sysDept.setDeptName(sysDeptVo.getDeptName());
            sysDept.setChief(sysDeptVo.getChief());
            sysDept.setEmail(sysDeptVo.getEmail());
            sysDept.setParentId(sysDeptVo.getParentId());
            sysDept.setSort(sysDeptVo.getSort());
            sysDept.setRemark(sysDeptVo.getRemark());
            UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
            updateWrapper.eq("id",sysDept.getId());
            Integer ret=this.baseMapper.update(sysDept,updateWrapper);
            if(ret!=null && ret>0) {
                Result r1 = sysDeptRoleService.update(sysDeptVo);
                if(r1.getCode().equals(HttpResultEnum.EDIT_CODE_200.getCode())) {
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
            UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<SysDept>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id",id);
            SysDept sysDept = this.baseMapper.selectById(id);
            Integer ret=this.baseMapper.update(sysDept,updateWrapper);
            if(ret!=null && ret>0) {
                Result r1 = sysDeptRoleService.delete(id);
                if(r1.getCode().equals(HttpResultEnum.DEL_CODE_200.getCode())) {
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
     * 检查部门编码是否存在
     * @param dicCode
     * @return
     */
    @Override
    public Result deptCodeIsExist(String dicCode) {
        Result r = null;
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dept_code",dicCode);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        Integer count = this.baseMapper.selectCount(queryWrapper);
        if(count!=null && count>0) {
            r = new Result(HttpResultEnum.DEPT_CODE_1.getCode(),HttpResultEnum.DEPT_CODE_1.getMessage());
        } else {
            r = new Result(HttpResultEnum.DEPT_CODE_0.getCode(),HttpResultEnum.DEPT_CODE_0.getMessage());
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
        UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_Y);
        SysDept sysDept =  this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(sysDept,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
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
        UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",id);
        updateWrapper.set("enabled", Const.ENABLED_N);
        SysDept sysDept = this.baseMapper.selectById(id);

        Integer ret = this.baseMapper.update(sysDept,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
        }
        return r;
    }

    /**
     * 根据id获得SysDeptVo
     * @param id
     * @return
     */
    @Override
    public SysDeptVo getSysDeptVoById(Integer id) {
        Map<String,Object> map = new HashMap<>();
        map.put("id",id);
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("enabled",Const.ENABLED_Y);
        if(1==id) {
            return this.getRootSysDeptVoById(Const.DEPT_ROOT);
        } else {
            return this.baseMapper.getSysDeptVoById(map);
        }
    }

    /**
     * 根据id获得根结点
     * @param rootCode
     * @return
     */
    @Override
    public SysDeptVo getRootSysDeptVoById(String rootCode) {
        Map<String,Object> map = new HashMap<>();
        map.put("rootCode",rootCode);
        map.put("delFlag",Const.DEL_FLAG_N);
        map.put("enabled",Const.ENABLED_Y);
        SysDeptVo sysDeptVo = this.baseMapper.getRootSysDeptVoById(map);
        sysDeptVo.setParentContent("结算系统");
        return  sysDeptVo;
    }

    /**
     * 根据部门id下的角色获得用户
     * @param deptId
     * @return
     */
    @Override
    public Result getDeptRoleUsers(Integer deptId){
        Map<String,Object> map = new HashMap<>();
        map.put("deptId",deptId);
        map.put("roleId",Const.ROLE_CODE_AM);
        Result r = new Result(HttpResultEnum.CODE_1.getCode(),HttpResultEnum.CODE_1.getMessage());
        List<SysDeptRoleUserVo> sysDeptRoleUserVos = this.baseMapper.getDeptRoleUsers(map);
        if(sysDeptRoleUserVos!=null) {
            r.setCode(HttpResultEnum.CODE_0.getCode());
            r.setMsg(HttpResultEnum.CODE_0.getMessage());
            r.setData(sysDeptRoleUserVos);
        }
        return r;
    }

    /**
     * 根据客户id获得所在的部门
     * @param customerId
     * @return
     */
    @Override
    public SysDept getDeptByCustomerId(Integer customerId) {
        return this.baseMapper.getDeptByCustomerId(customerId);
    }
}
