package com.settlement.controller;

import com.settlement.service.FileService;
import com.settlement.utils.FileResult;
import com.settlement.utils.HttpResultEnum;
import com.settlement.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *     文件上传类
 * </p>
 *
 * @auth admin
 * @date 2019-12-3
 */
@RestController
public class FileUploadController {
    @Autowired
    private FileService fileService;

    /**
     * @description 图片上传
     *
     * @param
     * @return
     */
    @PostMapping("/file/upload")
    public FileResult fileUpload(@RequestParam("file") MultipartFile file) {
        FileResult result = new FileResult();
        if (!file.isEmpty()) {
            Map<String,Object> map = fileService.uploadImage(file);
            if (HttpResultEnum.FILE_UPLOAD_CODE_9006.getCode().equals(map.get("result"))) {
                // 成功
                result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9006.getCode());
                result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9006.getMessage());
                result.setFileName(map.get("fileName").toString());
                result.setPath(map.get("path").toString());
            } else if (HttpResultEnum.FILE_UPLOAD_CODE_9000.getCode().equals(map.get("result"))) {
                result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9000.getCode());
                result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9000.getMessage());
            } else if (HttpResultEnum.FILE_UPLOAD_CODE_9004.getCode().equals(map.get("result"))) {
                result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9004.getCode());
                result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9004.getMessage());
            }
        } else {
            // 上传文件空
            result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9005.getCode());
            result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9005.getMessage());
        }
        return result;
    }

    /**
     * 考勤记录导入
     * @param file
     * @return
     */
    @PostMapping("/file/upload/workattendance")
    public FileResult workAttendanceImport(@RequestParam("file") MultipartFile file){
        FileResult result = new FileResult();
        if(!file.isEmpty()) {
            Map<String,Object> map = fileService.uploadImage(file);
            if (HttpResultEnum.FILE_UPLOAD_CODE_9006.getCode().equals(map.get("result"))) {
                // 成功
                result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9006.getCode());
                result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9006.getMessage());
                result.setFileName(map.get("oldFileName").toString());
                result.setPath(map.get("path").toString());
            } else if (HttpResultEnum.FILE_UPLOAD_CODE_9000.getCode().equals(map.get("result"))) {
                result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9000.getCode());
                result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9000.getMessage());
            } else if (HttpResultEnum.FILE_UPLOAD_CODE_9004.getCode().equals(map.get("result"))) {
                result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9004.getCode());
                result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9004.getMessage());
            }
        } else {
            // 上传文件空
            result.setCode(HttpResultEnum.FILE_UPLOAD_CODE_9005.getCode());
            result.setMsg(HttpResultEnum.FILE_UPLOAD_CODE_9005.getMessage());
        }
        return result;
    }

}
