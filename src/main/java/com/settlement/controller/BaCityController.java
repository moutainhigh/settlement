package com.settlement.controller;


import com.settlement.bo.PageData;
import com.settlement.co.CityCo;
import com.settlement.entity.BaCity;
import com.settlement.entity.SysUser;
import com.settlement.service.BaCityService;
import com.settlement.utils.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.dao.DataAccessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author kun
 * @since 2020-01-08
 */
@RestController
@RequestMapping("/ba-city")
public class BaCityController {
    @Autowired
    private BaCityService baCityService;
    /**
     * 列表显示
     * @param cityCo
     * @return
     */
    @GetMapping("/pagedata")
    public PageData listPageData(CityCo cityCo){
        PageData pageData = baCityService.listPageData(cityCo);
        return  pageData;
    }

    /**
     * 获取下拉城市列表
     * @param
     * @return
     */
    @GetMapping("/citySelect")
    public Result citySelect(){
        return this.baCityService.getBaCityListAjax();
    }

    /**
     * 添加
     * @param baCity
     * @return
     */
    @PostMapping("/add")
    public Result add(BaCity baCity){
        SysUser user = (SysUser) SecurityUtils.getSubject().getPrincipal();
        baCity.setCreateUserId(user.getId());
        baCity.setCreateTime(new Date());
        Result r = baCityService.add(baCity);
        return r;
    }

    /**
     *修改
     * @param baCity
     * @return
     */
    @PutMapping("/update")
    public Result update(BaCity baCity){
        Result r = baCityService.update(baCity);
        return r;
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public Result delete(@PathVariable Integer id){
        Result r = baCityService.delete(id);
        return r;
    }
    /**
     * 启用
     * @param id
     * @return
     */
    @PutMapping("/enable/start/{id}")
    public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return baCityService.updateEnableStart(id);
    }

    /**
     * 停用
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return baCityService.updateEnableStop(id);
    }
}
