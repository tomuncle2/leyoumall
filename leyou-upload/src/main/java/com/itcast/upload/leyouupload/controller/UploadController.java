package com.itcast.upload.leyouupload.controller;

import com.itcast.upload.leyouupload.service.UploadService;
import com.leyou.common.enums.ResultCodeEnum;
import com.leyou.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



/**
 * @author: 蔡迪
 * @date: 16:06 2020/9/5
 * @description: 文件上传controller
 */
@RestController
@Slf4j
@RequestMapping("file")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("upload")
    public Result upload(MultipartFile file) {
        String url = uploadService.upload(file);
        if (StringUtils.isBlank(url)) {
            return Result.success(url);
        } else {
            return Result.failure(ResultCodeEnum.OPERATION_FAILED);
        }
    }
}