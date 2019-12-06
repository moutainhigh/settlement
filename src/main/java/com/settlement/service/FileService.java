package com.settlement.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {
    /** 上传图片 */
    Map<String, Object> uploadImage(MultipartFile file);
    /** 考勤记录导入 **/
    Map<String, Object> workAttendanceImport( MultipartFile file);
}
