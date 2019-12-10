package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.ProjectEmployeeCo;
import com.settlement.entity.SysUser;
import com.settlement.service.BaProjectEmployeeService;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import com.settlement.vo.ProjectEmployeeVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

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
public class BaProjectEmployeeController {
    @Autowired
    private BaProjectEmployeeService baProjectEmployeeService;

    /**
     * @description 未提交员工数据
     *
     * @auth admin
     * @date 2019-11-28
     * @param projectEmployeeCo
     * @return
     */
    @GetMapping("/ba-project-employee/pagedata")
    public PageData getNoSubmitPageData(ProjectEmployeeCo projectEmployeeCo) {
        return this.baProjectEmployeeService.getNoSubmitEmployee(projectEmployeeCo);
    }

    /**
     * @description  检查员工编号是否存在
     *
     * @auth admin
     * @date 2019-12-6
     * @param code
     * @return
     */
    @GetMapping("/ba-project-employee/isexist/{code}")
    public Result checkEmpCodeIsExist(@PathVariable(value="code") String code) {
        return this.baProjectEmployeeService.checkEmpCodeIsExist(code);
    }

    /**
     * @description 新增员工
     *
     * @auth admin
     * @date 2019-12-6
     * @param projectEmployeeVo
     * @return
     */
    @PostMapping("/ba-project-employee/add")
    public Result addProjectEmployee(ProjectEmployeeVo projectEmployeeVo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Date d = new Date();
        projectEmployeeVo.setCreateTime(d);
        projectEmployeeVo.setCreateUserId(user.getId());
        projectEmployeeVo.setUpdateTime(d);
        projectEmployeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_N);
        projectEmployeeVo.setDelFlag(Const.DEL_FLAG_N);
        return this.baProjectEmployeeService.insertProjectEmp(projectEmployeeVo);
    }

    /**
     * @description 增加并提交员工
     *
     * @auth admin
     * @date 2019-12-6
     * @param projectEmployeeVo
     * @return
     */
    @PostMapping("/ba-project-employee/addsubmit")
    public Result addAndSubmitProjectEmployee(ProjectEmployeeVo projectEmployeeVo) {
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        Date d = new Date();
        projectEmployeeVo.setCreateTime(d);
        projectEmployeeVo.setCreateUserId(user.getId());
        projectEmployeeVo.setUpdateTime(d);
        projectEmployeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_S);
        projectEmployeeVo.setDelFlag(Const.DEL_FLAG_N);
        return this.baProjectEmployeeService.insertProjectEmp(projectEmployeeVo);
    }

    /**
     * @description 提交
     *
     * @auth admin
     * @date 2019-12-7
     * @param ids
     * @return
     */
    @PostMapping("/ba-project-employee/sub")
    public Result submitProjectEmp(String ids) {
        return this.baProjectEmployeeService.updateEmpSubByBatchId(ids);
    }

    /**
     * @description 删除员工
     *
     * @auth admin
     * @date 2019-12-9
     * @param id
     * @return
     */
    @DeleteMapping("/ba-project-employee/del/{id}")
    public Result deleteProjectEmp(@PathVariable Integer id) {
        return this.baProjectEmployeeService.deleteProjectEmp(id);
    }

    /**
     * @description 修改员工
     *
     * @auth admin
     * @date 2019-12-10
     * @param projectEmployeeVo
     * @return
     */
    @PutMapping("/ba-project-employee/edit")
    public Result editProjectEmp(ProjectEmployeeVo projectEmployeeVo) {
        projectEmployeeVo.setUpdateTime(new Date());
        projectEmployeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_N);
        return this.baProjectEmployeeService.updateProjectEmp(projectEmployeeVo);
    }

    /**
     * @description 员工修改并提交
     *
     * @auth admin
     * @date 2019-12-10
     * @param projectEmployeeVo
     * @return
     */
    @PutMapping("/ba-project-employee/editsubmit")
    public Result editSubmitProjectEmp(ProjectEmployeeVo projectEmployeeVo) {
        projectEmployeeVo.setUpdateTime(new Date());
        projectEmployeeVo.setSubStatus(Const.EMP_SUBMIT_STATUS_S);
        return this.baProjectEmployeeService.updateProjectEmp(projectEmployeeVo);
    }
}
