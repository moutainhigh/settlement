package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.ContractCo;
import com.settlement.co.CustomerCo;
import com.settlement.entity.SysUser;
import com.settlement.service.BaContractService;
import com.settlement.utils.Result;
import com.settlement.vo.BaContractVo;
import com.settlement.vo.BaCustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;

/**
 * <p>
 * 合同管理表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-12-03
 */
@RestController
@RequestMapping("/ba-contract")
public class BaContractController {

    @Autowired
    private BaContractService baContractService;

    /**
     *
     * @param contractCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(ContractCo contractCo){
        PageData pageData = baContractService.listPageData(contractCo);
        return  pageData;
    }

    /**
     * 添加
     * @param baContractVo
     * @return
     */
    @PostMapping("/add")
    public Result add(BaContractVo baContractVo, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        baContractVo.setCreateUserId(user.getId());
        Result r = baContractService.add(baContractVo);
        return r;
    }

    /**
     *修改
     * @param baContractVo
     * @return
     */
    @PutMapping("/update")
    public Result update(BaContractVo baContractVo){
        Result r = baContractService.update(baContractVo);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = baContractService.delete(id);
        return r;
    }
    /**
     * 检查是否存在contractNo
     * @param contractNo
     * @return
     */
    @GetMapping("/isexist/{contractNo}")
    public Result isExist(@PathVariable(value="contractNo") String contractNo) {
        return  baContractService.isExist(contractNo);
    }
}
