package com.settlement.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.settlement.bo.PageData;
import com.settlement.co.DataDicCo;
import com.settlement.entity.SysDataDic;
import com.settlement.service.SysDataDicService;
import com.settlement.utils.Const;
import com.settlement.utils.Result;
import io.swagger.models.auth.In;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * <p>
 * 数据字典表 前端控制器
 * </p>
 *
 * @author admin
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
//        IPage<SysDataDic> page = new Page<>(dataDicCo.getPage(),dataDicCo.getLimit());
//        QueryWrapper<SysDataDic> queryWrapper = new QueryWrapper<>();
//        queryWrapper.eq("del_flag", Const.DEL_FLAG_N);
//        queryWrapper.like(StringUtils.isNotBlank(dataDicCo.getKeyword()),"dic_content",dataDicCo.getKeyword());
//        sysDataDicService.page(page,queryWrapper);
//        return new PageData(page.getTotal(),page.getRecords());
    }

    /**
     * 添加数据字典
     * @param sysDataDic
     * @return
     */
    @PostMapping("/add")
    public Result dataDicAdd(SysDataDic sysDataDic) {
        sysDataDic.setCreateTime(new Date());
        sysDataDic.setEnabled(Const.ENABLED_Y);
        sysDataDic.setDelFlag(Const.DEL_FLAG_N);

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
    @DeleteMapping("/del/{id}")
    public Result dataDicDelete(Integer id) {
        return sysDataDicService.deleteDataDic(id);
    }
    @GetMapping("/pidSelect")
   public Result pidSelectData() {
        return sysDataDicService.pidSelectData();
   }
}
