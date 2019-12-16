package com.settlement.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.settlement.entity.SysDept;
import com.settlement.entity.SysUser;
import com.settlement.service.SysDeptService;
import com.settlement.utils.Result;
import com.settlement.utils.Status;
import com.settlement.vo.SysDeptVo;
import com.settlement.vo.SysPermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 * 部门表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/sys-dept")
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
    @GetMapping("/deptselect")
    public Result getDeptSelect() {
        return sysDeptService.getDeptSelect();
    }


    @PostMapping("/dtree/pagedata")
    public Object dtreePageData() {
        List<SysDeptVo> sysPermissions = sysDeptService.getDeptTreeList();
        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status", JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(sysPermissions));
        System.out.println(josn.toJSONString());
        return josn.toJSONString();
    }

    /**
     * 添加
     * @param sysDeptVo
     * @return
     */
    @PostMapping("/add")
    public Result add(SysDeptVo sysDeptVo, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        sysDeptVo.setCreateUserId(user.getId());
        Result r = sysDeptService.add(sysDeptVo);
        return r;
    }

    /**
     *修改
     * @param sysDeptVo
     * @return
     */
    @PutMapping("/update")
    public Result update(SysDeptVo sysDeptVo){
        Result r = sysDeptService.update(sysDeptVo);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = sysDeptService.delete(id);
        return r;
    }
    /**
     * 检查部门编码deptCode是否存在
     * @param deptCode
     * @return
     */
    @GetMapping("/isexist/{deptCode}")
    public Result dicCodeIsExist(@PathVariable(value="deptCode") String deptCode) {
        Result r = sysDeptService.deptCodeIsExist(deptCode);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return sysDeptService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return sysDeptService.updateEnableStop(id);
    }

}
