package com.leyou.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PublicKey;

/**
 * @author: 蔡迪
 * @date: 22:39 2020/9/25
 * @description: jwt配置
 */
@Slf4j
@ConfigurationProperties(prefix = "leyou.jwt")
@Data
public class JwtProperties {

    /**公钥地址*/
    private String pubKeyPath;

    /**公钥*/
    private PublicKey publicKey;

    private String cookieName;


    @PostConstruct
    private void init() {
        File pubFile = new File(pubKeyPath);
        try {
            // 获取公钥
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("获取公钥失败,{}", e.getCause());
            throw new RuntimeException();
        }
    }
}