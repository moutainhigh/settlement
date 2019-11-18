package com.settlement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 *@description  资源类
 *
 * @author admin
 * @date 2019/11/18.
 */
@Data
@Configuration
@ConfigurationProperties(prefix="remote",ignoreInvalidFields = false)
@PropertySource("classpath:config/remote.properties")
@Component
public class RemoteProperties {

    private String defaultPass;

}
