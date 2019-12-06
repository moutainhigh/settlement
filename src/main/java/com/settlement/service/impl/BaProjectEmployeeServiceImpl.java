package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.entity.BaProjectEmployee;
import com.settlement.mapper.BaProjectEmployeeMapper;
import com.settlement.service.BaProjectEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
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

    @Override
    public Result checkEmpCodeIsExist(String code) {
        Result r = new Result(HttpResultEnum.EMP_CODE_1.getCode(), HttpResultEnum.EMP_CODE_1.getMessage());
        QueryWrapper<BaProjectEmployee> queryWrapper = new QueryWrapper<BaProjectEmployee>();
        queryWrapper.eq("code",code);
        queryWrapper.eq("del_flag",Const.DEL_FLAG_N);
        int count = this.baseMapper.selectCount(queryWrapper);
        if (count == 0) {
            r.setCode(HttpResultEnum.EMP_CODE_0.getCode());
            r.setMsg(HttpResultEnum.EMP_CODE_0.getMessage());
        }
        return r;
    }

    @Override
    public Result insertProjectEmp(ProjectEmployeeVo projectEmployeeVo) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            int ret = this.baseMapper.insert(projectEmployeeVo);
            if (ret > 0) {
                r = new Result();
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
