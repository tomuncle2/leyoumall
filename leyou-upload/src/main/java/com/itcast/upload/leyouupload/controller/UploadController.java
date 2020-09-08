package com.itcast.upload.leyouupload.controller;

import com.itcast.upload.leyouupload.service.UploadService;
import com.leyou.common.enums.ResultCodeEnum;
import com.leyou.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



/**
 * @author: 蔡迪
 * @date: 16:06 2020/9/5
 * @description: 文件上传controller
 */
@RestController
@Slf4j
@RequestMapping("upload")
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("image")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
        String url = uploadService.upload(file);
        if (StringUtils.isNotBlank(url)) {
            return ResponseEntity.ok(url);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}