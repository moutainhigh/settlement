package com.settlement.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "message")
@PropertySource("classpath:config/file-message.properties")
@Data
public class FileMessageProperties {

    private Long fileSize;

    private Double scaleRatio;

    private String uploadPath;

    private String imageType;

}
