package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.EmployeeCo;
import com.settlement.entity.SysUser;
import com.settlement.service.BaEmployeeService;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import com.settlement.vo.EmployeeVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-28
 */
@RestController
public class BaEmployeeController {
    @Autowired
    private BaEmployeeService baEmployeeService;

    /**
     * @description 未提交员工数据
     *
     * @auth admin
     * @date 2019-11-28
     * @param employeeCo
     * @return
     */
    @GetMapping("/ba-employee/pagedata")
    public PageData getNoSubmitPageData(EmployeeCo employeeCo) {
        return this.baEmployeeService.getNoSubmitEmployee(employeeCo);
    }

    /**
     * @description  检查员工编号是否存在
     *
     * @auth admin
     * @date 2019-12-6
     * @param code
     * @return
     */
    @GetMapping("/ba-employee/isexist/{code}")
    public Result checkEmpCodeIsExist(@PathVariable(value="code") String code) {
        return this.baEmployeeService.checkEmpCodeIsExist(code);
    }

    /**
     * @description 新增员工
     *
     * @auth admin
     * @date 2019-12-6
     * @param employeeVo
     * @return
     */
    @PostMapping("/ba-employee/add")
    public Result addProjectEmployee(EmployeeVo employeeVo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Date d = new Date();
        employeeVo.setCreateTime(d);
        employeeVo.setCreateUserId(user.getId());
        employeeVo.setUpdateTime(d);
        employeeVo.setJobStatus(Const.JOB_STATUS_O);
        // employeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_N);
        employeeVo.setDelFlag(Const.DEL_FLAG_N);
        return this.baEmployeeService.insertProjectEmp(employeeVo, Const.EMP_SUBMIT_STATUS_N);
    }

    /**
     * @description 增加并提交员工
     *
     * @auth admin
     * @date 2019-12-6
     * @param employeeVo
     * @return
     */
    @PostMapping("/ba-employee/addsubmit")
    public Result addAndSubmitProjectEmployee(EmployeeVo employeeVo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Date d = new Date();
        employeeVo.setCreateTime(d);
        employeeVo.setCreateUserId(user.getId());
        employeeVo.setUpdateTime(d);
        employeeVo.setJobStatus(Const.JOB_STATUS_O);
        employeeVo.setEntranceStatus(Const.ENTRANCE_STATUS_I);
       // employeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_S);
        employeeVo.setDelFlag(Const.DEL_FLAG_N);
        return this.baEmployeeService.insertProjectEmp(employeeVo, Const.EMP_SUBMIT_STATUS_S);
    }

    /**
     * @description 删除员工
     *
     * @auth admin
     * @date 2019-12-9
     * @param id
     * @return
     */
    @DeleteMapping("/ba-employee/del/{id}")
    public Result deleteProjectEmp(@PathVariable Integer id) {
        return this.baEmployeeService.deleteProjectEmp(id);
    }

    /**
     * @description 修改员工
     *
     * @auth admin
     * @date 2019-12-10
     * @param employeeVo
     * @return
     */
    @PutMapping("/ba-employee/edit")
    public Result editProjectEmp(EmployeeVo employeeVo) {
        employeeVo.setUpdateTime(new Date());
        // employeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_N);
        return this.baEmployeeService.updateProjectEmp(employeeVo, null);
    }

    /**
     * @description 员工修改并提交
     *
     * @auth admin
     * @date 2019-12-10
     * @param employeeVo
     * @return
     */
    @PutMapping("/ba-employee/editsubmit")
    public Result editSubmitProjectEmp(EmployeeVo employeeVo) {
        employeeVo.setUpdateTime(new Date());
        // employeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_S);
        return this.baEmployeeService.updateProjectEmp(employeeVo, Const.EMP_SUBMIT_STATUS_S);
    }
}
