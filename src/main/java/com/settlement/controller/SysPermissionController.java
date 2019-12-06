package com.settlement.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.PermissionCo;
import com.settlement.entity.SysPermission;
import com.settlement.entity.SysUser;
import com.settlement.service.SysPermissionService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import com.settlement.utils.Status;
import com.settlement.vo.SysPermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * <p>
 * @desciption 功能菜单表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-07
 */
@RestController
@RequestMapping("/sys-permission")
public class SysPermissionController {

    @Autowired
    private SysPermissionService sysPermissionService;

    @GetMapping("menu")
    public Result getMenuList(HttpSession session) {
        // 从session中取得当前登陆用户角色
        SysUser user = (SysUser)session.getAttribute("user");
        // int roleId = user.getRoles().get(0).getId();
        List<SysPermissionVo> list = sysPermissionService.getMenu(1);
        Result r = new Result();
        r.setData(list);
        return r;
    }

    /**
     * 根据条件查询功能列表页面
     * @param permissionCo
     * @return
     */
    @GetMapping("/pagedata2")
    public PageData getPermissionList(PermissionCo permissionCo) {
        IPage<SysPermission> page = new Page<>(permissionCo.getPage(),permissionCo.getLimit());
        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(permissionCo.getKeyword()),"p_name",permissionCo.getKeyword());
        sysPermissionService.page(page,queryWrapper);
        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * ztree加载数据
     * @return
     */
    @PostMapping("/ztree/pagedata")
    public Object getPermissionTree() {
        List<SysPermissionVo> sysPermissions = sysPermissionService.getPermissionTreeData();
        List<SysPermissionVo> treeDataList = new ArrayList<>();
        //根据id添加到map当中
        Map<Integer,SysPermissionVo> map = new HashMap<>();
       for(SysPermissionVo sysPermission: sysPermissions){
           map.put(sysPermission.getId(),sysPermission);
           System.out.println(sysPermission);
       }
       //遍历查询生成树
       for(SysPermissionVo sysPermission: sysPermissions) {
           SysPermissionVo child = sysPermission;
           System.out.println("0-"+child.getPName());
           if(child.getParentId().equals(-1)) {
               treeDataList.add(child);
               System.out.println("1-child "+child.getPName());
           } else {
               SysPermissionVo parent=map.get(child.getParentId());
               System.out.println("2-parent "+parent.getPName());
               parent.getChildrens().add(child);
           }

       }
        System.out.println("--------------treeDataList---------------------");
        System.out.println(treeDataList);
        JSONObject josn = new JSONObject();
        josn.put("data", JSONArray.toJSON(treeDataList));
        return JSONArray.toJSON(treeDataList);

    }

    /**
     * dtree加载数据
     * @return
     */
    @PostMapping("/dtree/list/pagedata")
    public Object getPermissionDTreeList() {
        List<SysPermissionVo> sysPermissions = sysPermissionService.getPermissionTreeData();

        JSONObject josn = new JSONObject();
        Status status = new Status();
        status.setCode(200);
        status.setMessage("操作成功");
        josn.put("status",JSONArray.toJSON(status));
        josn.put("data", JSONArray.toJSON(sysPermissions));
        System.out.println(josn.toJSONString());
        return josn.toJSONString();
    }


    @PostMapping("/dtree/pagedata")
    public Object getPermissionDTree() {
        List<SysPermissionVo> sysPermissions = sysPermissionService.getPermissionTreeData();
        List<SysPermissionVo> treeDataList = new ArrayList<>();
        //根据id添加到map当中
        Map<Integer,SysPermissionVo> map = new HashMap<>();
        for(SysPermissionVo sysPermission: sysPermissions){
            map.put(sysPermission.getId(),sysPermission);
        }
        //遍历查询生成树
        for(SysPermissionVo sysPermission: sysPermissions) {
            SysPermissionVo child = sysPermission;
            System.out.println("0-"+child.getPName());
            if(child.getParentId().equals(-1)) {
                treeDataList.add(child);
                System.out.println("1-child "+child.getPName());
            } else {
                SysPermissionVo parent=map.get(child.getParentId());
                System.out.println("2-parent "+parent.getPName());
                parent.getChildrens().add(child);
            }

        }
        JSONObject josn = new JSONObject();
        josn.put("code",0);
        josn.put("msg","操作成功");
        josn.put("data", JSONArray.toJSON(treeDataList));
        System.out.println(josn.toJSONString());
        return josn.toJSONString();

    }
    @GetMapping("/pagedata3")
    public Object getPermissionTree3() {
        List<SysPermissionVo> sysPermissions = sysPermissionService.getPermissionTreeData();
        List<SysPermissionVo> treeDataList = new ArrayList<>();
        //根据id添加到map当中
        Map<Integer,SysPermissionVo> map = new HashMap<>();
        for(SysPermissionVo sysPermission: sysPermissions){
            map.put(sysPermission.getId(),sysPermission);
        }
        //遍历查询生成树
        for(SysPermissionVo sysPermission: sysPermissions) {
            SysPermissionVo child = sysPermission;
            if(child.getParentId().equals("-1")) {
                treeDataList.add(child);
            } else {
                SysPermissionVo parent=map.get(child.getParentId());
                parent.getChildrens().add(child);
            }

        }
        System.out.println("--------------treeDataList---------------------");
        System.out.println(treeDataList);
        return treeDataList;
    }


    /**
     * 添加
     * @param sysPermission
     * @return
     */
    @PostMapping("/add")
    public Result add(SysPermission sysPermission, HttpSession session){
        SysUser user = (SysUser)session.getAttribute("user");
        sysPermission.setCreateUserId(user.getId());
        return sysPermissionService.add(sysPermission);
        //redirect:表示重定向到一个地址 redirect:/iframeContent
        //forward 表示转发到一个地址
    }

    /**
     * 修改
     * @param sysPermission
     * @return
     */
    @PostMapping("/update")
    public Result update(SysPermission sysPermission) {
        return  sysPermissionService.update(sysPermission);
    }

    /**
     * 删除菜单
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        return sysPermissionService.delete(id) ;
    }

    /**
     * 加载单个结点
     * @param id
     * @return
     */
    @GetMapping("/loadnode")
    public Object loadnode(Integer id) {

        QueryWrapper<SysPermission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        return  sysPermissionService.getOne(queryWrapper);
    }
}
