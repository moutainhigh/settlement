package com.settlement.controller;


import com.settlement.service.BaLevelPriceService;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 级别单价表 前端控制器
 * </p>
 *
 * @author admin
 * @since 2019-12-05
 */
@RestController
public class BaLevelPriceController {
    @Autowired
    private BaLevelPriceService baLevelPriceService;

    /**
     * @description 根据ID级别单价
     *
     * @auth admin
     * @date 2019-12-6
     * @param id
     * @return
     */
    @GetMapping("/ba-level-price/{id}")
    public Result getLevelPriceById(@PathVariable(value="id") Integer id) {
        return this.baLevelPriceService.getLevelPriceById(id);
    }

}
