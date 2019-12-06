package com.settlement.service.impl;

import com.settlement.config.FileMessageProperties;
import com.settlement.service.FileService;
import com.settlement.utils.HttpResultEnum;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 *     文件处理业务逻辑类
 * </p>
 *
 * @auth admin
 * @date 2019-12-03
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMessageProperties config;

    @Override
    public Map<String, Object> uploadImage(MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        String code = HttpResultEnum.FILE_UPLOAD_CODE_9006.getCode();
        try {
            String[] imageType = config.getImageType().split(",");
            boolean flag = false;
            for (String type : imageType) {
                if (StringUtils.endsWithIgnoreCase(file.getOriginalFilename(), type)) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
               // String fileType = file.getContentType();
                String oldFileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(File.separator) + 1);
                String extensions = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
                String newFileName = uuid + "." + extensions;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String baseDir = sdf.format(new Date());
                String path = config.getUploadPath() + File.separator + baseDir + File.separator + newFileName;
                File uploadFile = new File(path);
                if (!uploadFile.exists()) {
                    uploadFile.mkdirs();
                }
                file.transferTo(uploadFile);
                if (file.getSize() > config.getFileSize()) {
                    Thumbnails.of(uploadFile).scale(config.getScaleRatio()).toFile(path);
                }
                map.put("oldFileName",oldFileName);
                map.put("path",path);
            } else {
                code = HttpResultEnum.FILE_UPLOAD_CODE_9004.getCode();

            }
        } catch (Exception e) {
            e.printStackTrace();
            code = HttpResultEnum.FILE_UPLOAD_CODE_9000.getCode();
        }
        map.put("result",code);
        return map;
    }

    /**
     * 考勤记录导入
     * @param file
     * @return
     */
    @Override
    public Map<String, Object> workAttendanceImport( MultipartFile file){
        Map<String, Object> map = new HashMap<>();
        String code = HttpResultEnum.FILE_UPLOAD_CODE_9006.getCode();
        try{
            //从配置文件读取文件类型
            String[] fileTypes = config.getFileType().split(",");
            boolean flag = false;
            for(String type:fileTypes) {
                if(StringUtils.endsWithIgnoreCase(file.getOriginalFilename(),type)){
                    flag = true;
                    break;
                }
            }
            //符合上传文件的类型开始执行上传操作
            if(flag) {
                String uuid = UUID.randomUUID().toString().replaceAll("-","");
                String oldFileName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(File.separator) + 1);
                String extensions = oldFileName.substring(oldFileName.lastIndexOf(".") + 1);
                String newFileName = uuid + "." + extensions;
                //根据当前月份在上传目录下生成一个日期的目录
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String baseDir = sdf.format(new Date());
                //拼接生成一个日期的目录
                String path = config.getWorkAttendanceImportPath() + File.separator + baseDir + File.separator + newFileName;
                File uploadFile = new File(path);
                if (!uploadFile.exists()) {
                    uploadFile.mkdirs();
                }
                file.transferTo(uploadFile);
                //判断文件的大小是否在限制范围内
                if (file.getSize() > config.getWorkAttendsFileSize()) {
                    Thumbnails.of(uploadFile).scale(config.getScaleRatio()).toFile(path);
                }
                map.put("oldFileName",oldFileName);
                map.put("path",path);
            }else {
                code = HttpResultEnum.FILE_UPLOAD_CODE_9004.getCode();

            }
        }catch (Exception e) {
            e.printStackTrace();
            code = HttpResultEnum.FILE_UPLOAD_CODE_9000.getCode();
        }
        map.put("result",code);
        return  map;
    }
}
