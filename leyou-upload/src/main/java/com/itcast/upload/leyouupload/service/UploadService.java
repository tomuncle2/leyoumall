package com.itcast.upload.leyouupload.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author: 蔡迪
 * @date: 15:47 2020/9/5
 * @description: 文件上传
 */
public interface UploadService {

    /**
     * 文件上传
     * @date 15:50 2020/9/5
     * @param file
     * @return java.lang.String
     */
    String upload(MultipartFile file);
}