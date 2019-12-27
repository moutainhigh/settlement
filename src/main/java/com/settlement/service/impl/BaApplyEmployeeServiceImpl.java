package com.settlement.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.EmpApplyCo;
import com.settlement.entity.BaApplyEmployee;
import com.settlement.mapper.BaApplyEmployeeMapper;
import com.settlement.service.BaApplyEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.vo.EmployeeVo;
import com.settlement.vo.ProjectGroupVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 申请员工关联 服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-12-23
 */
@Service
@Transactional
public class BaApplyEmployeeServiceImpl extends ServiceImpl<BaApplyEmployeeMapper, BaApplyEmployee> implements BaApplyEmployeeService {

    @Override
    public PageData getApplyEmpPageList(EmpApplyCo empApplyCo) {
        empApplyCo.setLevelModeF(Const.LEVEL_MODE_F);
        empApplyCo.setLevelModeH(Const.LEVEL_MODE_H);
        Page<EmployeeVo> page = new Page<EmployeeVo>(empApplyCo.getPage(), empApplyCo.getLimit());
        page.setRecords(this.baseMapper.getApplyEmpList(empApplyCo, page));
        return new PageData(page.getTotal(),page.getRecords());
    }
}
