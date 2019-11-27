package com.settlement.controller;


import com.settlement.service.SysDeptService;
import com.settlement.utils.Result;
import com.settlement.vo.SysDeptVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-11-07
 */
@Controller
public class SysDeptController {

    @Autowired
    private SysDeptService sysDeptService;

    /**
     * @description 部门下拉框
     *
     * @author admin
     * @date 2019-11-13
     * @return
     */
    @ResponseBody
    @GetMapping("/sys-dept/deptselect")
    public Result getDeptSelect() {
        return sysDeptService.getDeptSelect();
    }



}
