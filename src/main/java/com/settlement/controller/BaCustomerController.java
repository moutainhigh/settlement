package com.settlement.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.settlement.bo.PageData;
import com.settlement.co.CustomerCo;
import com.settlement.entity.SysUser;
import com.settlement.service.BaCustomerService;
import com.settlement.utils.Result;
import com.settlement.utils.Status;
import com.settlement.vo.BaCustomerAndProjectTreeVo;
import com.settlement.vo.BaCustomerVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-28
 */
@RestController
@RequestMapping("/ba-customer")
public class BaCustomerController {
    @Autowired
    private BaCustomerService baCustomerService;
    /**
     *
     * @param customerCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(CustomerCo customerCo){
        PageData pageData = baCustomerService.listPageData(customerCo);
        return  pageData;
    }
    @PostMapping("/list/tree1")
    public Object customerAndProjectTree(){
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = baCustomerService.getCustomerAndProjectTreeByUserId(37);
        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status",JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(baCustomerAndProjectTreeVos));
        System.out.println(josn.toJSONString());
        return josn.toJSONString();
    }
    @PostMapping("/list/tree2")
    public List<BaCustomerAndProjectTreeVo> customerAndProjectTree2(){
        List<BaCustomerAndProjectTreeVo> baCustomerAndProjectTreeVos = baCustomerService.getCustomerAndProjectTreeByUserId(37);
        return  baCustomerAndProjectTreeVos;
    }
    /**
     * 添加
     * @param baCustomerVo
     * @return
     */
    @PostMapping("/add")
    public Result add(BaCustomerVo baCustomerVo, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        baCustomerVo.setCreateUserId(user.getId());
        Result r = baCustomerService.add(baCustomerVo);
        return r;
    }

    /**
     *修改
     * @param baCustomerVo
     * @return
     */
    @PutMapping("/update")
    public Result update(BaCustomerVo baCustomerVo){
        Result r = baCustomerService.update(baCustomerVo);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = baCustomerService.delete(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baCustomerService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baCustomerService.updateEnableStop(id);
    }

}
