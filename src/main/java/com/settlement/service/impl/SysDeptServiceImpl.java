package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.settlement.entity.SysDept;
import com.settlement.mapper.SysDeptMapper;
import com.settlement.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import com.settlement.vo.SysDeptVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 部门表 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public Result getDeptSelect() {
        QueryWrapper<SysDept> queryWrapper = new QueryWrapper<SysDept>();
        queryWrapper.ne("dept_code", Const.DEPT_ROOT);
        queryWrapper.orderByAsc("sort");
        List<SysDept> deptList = this.baseMapper.selectList(queryWrapper);
        List<SelectVo> deptSelectVoList = new ArrayList<SelectVo>();
        if (deptList.size() > 0) {
            for (SysDept dept : deptList) {
                if (dept.getParentId() == Const.DEPT_ROOT_ID) {
                    SelectVo dsv = new SelectVo(dept.getId(),dept.getDeptName());
                    dsv.setChildren(new ArrayList<SelectVo>());
                    for (SysDept  deptChild : deptList) {
                        if (deptChild.getParentId() == dept.getId()) {
                            dsv.getChildren().add(new SelectVo(deptChild.getId(), deptChild.getDeptName()));
                        }
                    }
                    deptSelectVoList.add(dsv);
                }
            }
        }
        Result r = new Result(HttpResultEnum.CODE_0.getCode(), HttpResultEnum.CODE_0.getMessage());
        r.setData(deptSelectVoList);
        return r;
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
     * @param sysDept
     * @return
     */
    @Override
    public Result add(SysDept sysDept) {
        Result r =  new Result(HttpResultEnum.ADD_CODE_500.getCode(),HttpResultEnum.ADD_CODE_500.getMessage());
        sysDept.setDelFlag(Const.DEL_FLAG_N);
        sysDept.setEnabled(Const.ENABLED_Y);
        sysDept.setCreateTime(new Date());
        Integer ret = this.baseMapper.insert(sysDept);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
            r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
        }
        return  r;

    }

    /**
     * 修改
     * @param sysDept
     * @return
     */
    @Override
    public Result update(SysDept sysDept) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        UpdateWrapper<SysDept> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id",sysDept.getId());
        Integer ret=this.baseMapper.update(sysDept,updateWrapper);
        if(ret!=null && ret>0) {
            r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
            r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
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
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();

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
}
