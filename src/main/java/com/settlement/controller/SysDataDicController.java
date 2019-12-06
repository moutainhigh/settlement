package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.DataDicCo;
import com.settlement.entity.SysDataDic;
import com.settlement.entity.SysUser;
import com.settlement.service.SysDataDicService;
import com.settlement.utils.Const;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.HttpStateEnum;
import com.settlement.utils.Result;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author kun
 * @since 2019-11-19
 */
@RestController
@RequestMapping("/sys-data-dic")
public class SysDataDicController {

    @Autowired
    private SysDataDicService sysDataDicService;

    @GetMapping("/pagedata")
    public PageData getPageData(DataDicCo dataDicCo) {
          return  sysDataDicService.listPageData(dataDicCo);
    }

    /**
     * 添加数据字典
     * @param sysDataDic
     * @return
     */
    @PostMapping("/add")
    public Result dataDicAdd(SysDataDic sysDataDic, HttpSession session) {
        sysDataDic.setCreateTime(new Date());
        sysDataDic.setEnabled(Const.ENABLED_Y);
        sysDataDic.setDelFlag(Const.DEL_FLAG_N);
        SysUser user = (SysUser)session.getAttribute("user");
        sysDataDic.setCreateUserId(user.getId());
        Result r = sysDataDicService.saveDataDic(sysDataDic);
        return r;
    }

    /**
     * 修改数据字典
     * @param sysDataDic
     * @return
     */
    @PutMapping("/update")
    public Result dataDicUpdate(SysDataDic sysDataDic) {
        return sysDataDicService.updateDataDic(sysDataDic);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @PutMapping("/del/{id}")
    public Result dataDicDelete(@PathVariable(value="id") Integer id) {
        return sysDataDicService.deleteDataDic(id);
    }

    /**
     * 下拉列表数据加载
     * @return
     */
    @GetMapping("/pidSelect")
   public Result pidSelectData() {
        return sysDataDicService.pidSelectData();
   }

    /**
     * 检查dicCode是否存在
     * @param dicCode
     * @return
     */
   @GetMapping("/isexist/{dicCode}")
   public Result dicCodeIsExist(@PathVariable(value="dicCode") String dicCode) {
       Result r = sysDataDicService.dicCodeIsExist(dicCode);
       return r;
   }
    /**
     * 启用数据字典
     * @param id
     * @return
     */
   @PutMapping("/enable/start/{id}")
   public Result updateEnableStart(@PathVariable(value="id") Integer id){
        return sysDataDicService.updateDataDicStart(id);
    }

    /**
     * 停用数据字典
     * @param id
     * @return
     */
    @PutMapping("/enable/stop/{id}")
    public Result updateEnableStop(@PathVariable(value="id") Integer id){
        return sysDataDicService.updateDataDicStop(id);
    }

    @GetMapping("/pagedata/list")
    public Result pagedataList(DataDicCo dataDicCo) {
        return null;
    }
}
