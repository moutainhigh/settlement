package com.settlement.config;

import com.settlement.utils.Const;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  thymeleaf 引入页面常量
 * </p>
 */
@Configuration
public class ThymeleafConfig {

    @Resource
    private void configureThymeleafStatic(ThymeleafViewResolver viewResolver) {
        if (viewResolver != null) {
            Map<String, Object> vars = new HashMap<String, Object>();
            vars.put("PG_CHECKSTATUS_NO_CHECK", Const.CHECK_STATUS_NO_CHECK);
            vars.put("PG_CHECKSTATUS_CHECK_PASS",Const.CHECK_STATUS_CHECK_PASS);
            vars.put("PG_CHECKSTATUS_CHECK_PASS",Const.CHECK_STATUS_CHECK_PASS);
            vars.put("PG_CHECKSTATUS_CHECK_NOPASS",Const.CHECK_STATUS_CHECK_NOPASS);
            viewResolver.setStaticVariables(vars);

        }
    }

}
