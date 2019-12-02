package com.settlement.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.entity.BaProjectEmployee;
import com.settlement.mapper.BaProjectEmployeeMapper;
import com.settlement.service.BaProjectEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.vo.ProjectEmployeeVo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
@Service
@Transactional
public class BaProjectEmployeeServiceImpl extends ServiceImpl<BaProjectEmployeeMapper, BaProjectEmployee> implements BaProjectEmployeeService {

    @Override
    public PageData getNoSubmitEmployee(ProjectEmployeeCo projectEmployeeCo) {
        projectEmployeeCo.setSubmitStatus(Const.EMP_SUBMIT_STATUS_N);
        projectEmployeeCo.setDelFlag(Const.ENABLED_N);
        Page<ProjectEmployeeVo> page = new Page<ProjectEmployeeVo>(projectEmployeeCo.getPage(), projectEmployeeCo.getLimit());
        List<ProjectEmployeeVo> list = this.baseMapper.getNoSubmitProjectEmployeeList(projectEmployeeCo,page);
        page.setRecords(list);
        return new PageData(page.getTotal(),page.getRecords());
    }
}
