package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
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

import java.util.ArrayList;
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

    @Override
    public Result updateEmpSubByBatchId(String ids) {
        Result r = new Result(HttpResultEnum.CODE_500.getCode(), HttpResultEnum.CODE_500.getMessage());
        try {
            if (ids != null && !"".equals(ids)) {
                List<BaProjectEmployee> list = new ArrayList<BaProjectEmployee>();
                BaProjectEmployee emp = null;
                String[] idArr = ids.split(",");
                for (int i = 0; i < idArr.length; i++) {
                    emp = new BaProjectEmployee();
                    emp.setId(Integer.valueOf(idArr[i]));
                    emp.setSubStatus(Const.EMP_SUBMIT_STATUS_S);
                    list.add(emp);
                }
                int ret = this.baseMapper.updateEmpSubStatusBatchById(list);
                if (ret > 0) {
                    r.setCode(HttpResultEnum.CODE_200.getCode());
                    r.setMsg(HttpResultEnum.CODE_200.getMessage());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result deleteProjectEmp(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(), HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<BaProjectEmployee> updateWrapper = new UpdateWrapper<BaProjectEmployee>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id", id);
            int ret = this.baseMapper.update(new BaProjectEmployee().setId(id), updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.DEL_CODE_200.getCode());
                r.setMsg(HttpResultEnum.DEL_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public BaProjectEmployee getProjectEmpById(Integer id) {
        QueryWrapper<BaProjectEmployee> queryWrapper = new QueryWrapper<BaProjectEmployee>();
        queryWrapper.eq("id",id);
        BaProjectEmployee emp = this.baseMapper.selectById(id);
        return emp;
    }

    @Override
    public Result updateProjectEmp(ProjectEmployeeVo projectEmployeeVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaProjectEmployee> updateWrapper = new UpdateWrapper<BaProjectEmployee>();
            updateWrapper.set("emp_name", projectEmployeeVo.getEmpName());
            updateWrapper.set("entrance_time",projectEmployeeVo.getEntranceTime());
            updateWrapper.set("card_no",projectEmployeeVo.getCardNo());
            updateWrapper.set("place",projectEmployeeVo.getPlace());
            updateWrapper.set("responsible_person",projectEmployeeVo.getResponsiblePerson());
            updateWrapper.set("rp_email",projectEmployeeVo.getRpEmail());
            updateWrapper.set("send_copy_email",projectEmployeeVo.getSendCopyEmail());
            updateWrapper.set("position",projectEmployeeVo.getPosition());
            updateWrapper.set("level_mode",projectEmployeeVo.getLevelMode());
            updateWrapper.set("pos_level",projectEmployeeVo.getPosLevel());
            updateWrapper.set("price_month",projectEmployeeVo.getPriceMonth());
            updateWrapper.set("unit",projectEmployeeVo.getUnit());
            updateWrapper.set("price_day",projectEmployeeVo.getPriceDay());
            updateWrapper.set("claim_expense_person",projectEmployeeVo.getClaimExpensePerson());
            updateWrapper.set("work_attendence_person",projectEmployeeVo.getWorkAttendencePerson());
            updateWrapper.set("salary_month",projectEmployeeVo.getSalaryMonth());
            updateWrapper.set("salary_day",projectEmployeeVo.getSalaryDay());
            updateWrapper.set("upload_rate_email",projectEmployeeVo.getUploadRateEmail());
            updateWrapper.set("rate_email_filename",projectEmployeeVo.getRateEmailFilename());
            updateWrapper.set("sub_status",projectEmployeeVo.getSubStatus());
            updateWrapper.set("update_time",projectEmployeeVo.getUpdateTime());
            updateWrapper.eq("id",projectEmployeeVo.getId());
            int ret = this.baseMapper.update(projectEmployeeVo, updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }
}
