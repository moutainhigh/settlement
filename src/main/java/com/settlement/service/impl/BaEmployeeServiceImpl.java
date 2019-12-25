package com.settlement.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.EmployeeCo;
import com.settlement.entity.BaEmployee;
import com.settlement.entity.BaPgEmp;
import com.settlement.mapper.BaEmployeeMapper;
import com.settlement.mapper.BaPgEmpMapper;
import com.settlement.service.BaEmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.vo.EmployeeVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Date;
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
public class BaEmployeeServiceImpl extends ServiceImpl<BaEmployeeMapper, BaEmployee> implements BaEmployeeService {
    @Autowired
    private BaPgEmpMapper baPgEmpMapper;

    @Override
    public PageData getEmployeeList(EmployeeCo employeeCo) {
        Page<EmployeeVo> page = new Page<EmployeeVo>(employeeCo.getPage(), employeeCo.getLimit());
        List<EmployeeVo> list = this.baseMapper.getEmployeeList(employeeCo,page);
        page.setRecords(list);
        return new PageData(page.getTotal(),page.getRecords());
    }


    @Override
    public Result checkEmpCodeIsExist(String code) {
        Result r = new Result(HttpResultEnum.EMP_CODE_1.getCode(), HttpResultEnum.EMP_CODE_1.getMessage());
        QueryWrapper<BaEmployee> queryWrapper = new QueryWrapper<BaEmployee>();
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
    public Result insertProjectEmp(EmployeeVo employeeVo) {
        Result r = new Result(HttpResultEnum.ADD_CODE_500.getCode(), HttpResultEnum.ADD_CODE_500.getMessage());
        try {
            int ret = this.baseMapper.insert(employeeVo);
            BaPgEmp bpe = new BaPgEmp();
            bpe.setEmpId(employeeVo.getId());
            bpe.setPgId(employeeVo.getPgId());
            bpe.setOperTime(new Date());
            if (Const.EMP_ENTRANCE_STATUS_I.equals(employeeVo.getEntranceStatus())) {
                bpe.setEntranceStatus(Const.EMP_ENTRANCE_STATUS_I);
            } else if (Const.EMP_ENTRANCE_STATUS_N.equals(employeeVo.getEntranceStatus()))  {
                bpe.setEntranceStatus(Const.EMP_ENTRANCE_STATUS_N);
            }
            int ret1 = this.baPgEmpMapper.insert(bpe);
            if (ret > 0 && ret1 > 0) {
                r.setCode(HttpResultEnum.ADD_CODE_200.getCode());
                r.setMsg(HttpResultEnum.ADD_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }


    @Override
    public Result deleteProjectEmp(Integer id) {
        Result r = new Result(HttpResultEnum.DEL_CODE_500.getCode(), HttpResultEnum.DEL_CODE_500.getMessage());
        try {
            UpdateWrapper<BaEmployee> updateWrapper = new UpdateWrapper<BaEmployee>();
            updateWrapper.set("del_flag", Const.DEL_FLAG_D);
            updateWrapper.eq("id", id);
            int ret = this.baseMapper.update(new BaEmployee().setId(id), updateWrapper);
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
    public BaEmployee getProjectEmpById(Integer id) {
        QueryWrapper<BaEmployee> queryWrapper = new QueryWrapper<BaEmployee>();
        queryWrapper.eq("id",id);
        BaEmployee emp = this.baseMapper.selectById(id);
        return emp;
    }

    @Override
    public Result updateProjectEmp(EmployeeVo employeeVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            UpdateWrapper<BaEmployee> updateWrapper = new UpdateWrapper<BaEmployee>();
            /*updateWrapper.set("emp_name", employeeVo.getEmpName());
            updateWrapper.set("entrance_time", employeeVo.getEntranceTime());
            updateWrapper.set("card_no", employeeVo.getCardNo());
            updateWrapper.set("place", employeeVo.getPlace());
            updateWrapper.set("responsible_person", employeeVo.getResponsiblePerson());
            updateWrapper.set("rp_email", employeeVo.getRpEmail());
            updateWrapper.set("send_copy_email", employeeVo.getSendCopyEmail());
            updateWrapper.set("position", employeeVo.getPosition());
            updateWrapper.set("level_mode", employeeVo.getLevelMode());
            updateWrapper.set("pos_level", employeeVo.getPosLevel());
            updateWrapper.set("price_month", employeeVo.getPriceMonth());
            updateWrapper.set("unit", employeeVo.getUnit());
            updateWrapper.set("price_day", employeeVo.getPriceDay());
            updateWrapper.set("claim_expense_person", employeeVo.getClaimExpensePerson());
            updateWrapper.set("work_attendence_person", employeeVo.getWorkAttendencePerson());
            updateWrapper.set("salary_month", employeeVo.getSalaryMonth());
            updateWrapper.set("salary_day", employeeVo.getSalaryDay());
            updateWrapper.set("upload_rate_email", employeeVo.getUploadRateEmail());
            updateWrapper.set("rate_email_filename", employeeVo.getRateEmailFilename());
            // updateWrapper.set("sub_status", employeeVo.getSubStatus());
            updateWrapper.set("update_time", employeeVo.getUpdateTime());*/
            employeeVo.setUpdateTime(new Date());
            updateWrapper.eq("id", employeeVo.getId());
            int ret = this.baseMapper.update(employeeVo, updateWrapper);
            if (ret > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return r;
    }

    @Override
    public Result updateProjectEmpSubmit(EmployeeVo employeeVo) {
        Result r = new Result(HttpResultEnum.EDIT_CODE_500.getCode(),HttpResultEnum.EDIT_CODE_500.getMessage());
        try {
            employeeVo.setUpdateTime(new Date());
            employeeVo.setEntranceStatus(Const.EMP_ENTRANCE_STATUS_I);
            UpdateWrapper<BaEmployee> updateWrapper = new UpdateWrapper<BaEmployee>();
            updateWrapper.eq("id", employeeVo.getId());
            int ret = this.baseMapper.update(employeeVo, updateWrapper);
            UpdateWrapper<BaPgEmp> updateWrapper1 = new UpdateWrapper<BaPgEmp>();
            BaPgEmp bpe = new BaPgEmp();
            bpe.setEntranceStatus(Const.EMP_ENTRANCE_STATUS_I);
            bpe.setEmpId(employeeVo.getId());
            bpe.setOperTime(new Date());
            bpe.setPgId(employeeVo.getPgId());
            updateWrapper1.eq("pg_id",bpe.getPgId());
            updateWrapper1.eq("emp_id",bpe.getEmpId());
            int ret1 = this.baPgEmpMapper.update(bpe, updateWrapper1);
            if (ret > 0 && ret1 > 0) {
                r.setCode(HttpResultEnum.EDIT_CODE_200.getCode());
                r.setMsg(HttpResultEnum.EDIT_CODE_200.getMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return r;
    }

    @Override
    public List<EmployeeVo> getApplyUpdateEmps(String ids) {
        List<EmployeeVo> empList = new ArrayList<EmployeeVo>();
        if (StringUtils.isNotBlank(ids)) {
            String[] idArr = ids.split(",");
            EmployeeVo ev = null;
            for (int i = 0 ; i < idArr.length; i++) {
                BaEmployee be = this.baseMapper.selectById(Integer.valueOf(idArr[i]));
                ev = new EmployeeVo();
                ev.setId(be.getId());
                ev.setEmpName(be.getEmpName());
                ev.setCode(be.getCode());
                empList.add(ev);
            }
        }
        return empList;
    }
}
