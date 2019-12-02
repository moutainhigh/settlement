package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.settlement.entity.SysDept;
import com.settlement.mapper.SysDeptMapper;
import com.settlement.service.SysDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.SelectVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (deptList != null && deptList.size() > 0) {
            deptSelectVoList = buildDeptTree(deptList);
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
}
