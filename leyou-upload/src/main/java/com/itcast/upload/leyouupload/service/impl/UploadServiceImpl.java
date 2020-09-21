package com.itcast.upload.leyouupload.service.impl;

import com.itcast.upload.leyouupload.service.UploadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 15:51 2020/9/5
 * @description: 文件上传impl
 */
@Service
@Slf4j
public class UploadServiceImpl implements UploadService {

    /**允许文件类型*/
    private static final List<String> CONTENT_TYPES = Arrays.asList("image/png","image/jpeg", "image/gif");

    /**
     * 文件上传
     * @date 15:50 2020/9/5
     * @param file
     * @return java.lang.String
     */
    @Override
    public String upload(MultipartFile file) {
        // 文件大小 类型 内容
        String fileName = file.getOriginalFilename();
        if (!CONTENT_TYPES.contains(file.getContentType())) {
            log.error("[leyou-upload]===upload 文件类型错误!! 文件名：{}", fileName);
            return null;
        }
        // 内容校验
        try {
            BufferedImage bufferedImage = ImageIO.read(file.getInputStream());
            if (null == bufferedImage) {
                log.error("[leyou-upload]===upload 文件内容不合法!! 文件名：{}", fileName);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        // 保存到服务器
        try {
            file.transferTo(new File("D:\\LeyouStaticResource\\upload\\" + fileName));
            // nginx代理的图片资源地址（这里放本地，实际是放在专门的服务器上）
            return "http://image.leyou.com/upload/" + fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}