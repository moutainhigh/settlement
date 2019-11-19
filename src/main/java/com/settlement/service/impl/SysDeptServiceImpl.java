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
}
