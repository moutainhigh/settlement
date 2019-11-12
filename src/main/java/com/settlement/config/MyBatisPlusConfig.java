package com.settlement.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Administrator on 2019/11/07.
 */
@Configuration
public class MyBatisPlusConfig {

    /**
     * @description: 配置分页插件
     *
     * @author wq
     * @date 2019-11-07
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

}
