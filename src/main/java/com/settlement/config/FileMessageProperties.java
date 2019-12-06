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
    /**上传文件类型**/
    private String fileType;
    /**考勤文件存储路径**/
    private String workAttendanceImportPath;
    /**上传考勤文件大小限制**/
    private Long workAttendsFileSize;

}
