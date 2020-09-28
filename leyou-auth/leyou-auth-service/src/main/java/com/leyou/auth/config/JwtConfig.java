package com.leyou.auth.config;

import com.leyou.auth.utils.RsaUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;
import java.io.File;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author: 蔡迪
 * @date: 17:48 2020/9/25
 * @description: jwt 参数配置类
 */
@ConfigurationProperties(prefix = "leyou.jwt")
@Data
@Slf4j
public class JwtConfig {
    /**生成公钥和私匙需要的密匙*/
    private String secret;
    /**公钥*/
    private String pubKeyPath;
    /**私钥*/
    private String priKeyPath;
    /**token过期时间*/
    private int expire;
    /**公钥*/
    private PublicKey publicKey;
    /**私钥*/
    private PrivateKey privateKey;
    /**cookieName*/
    private String cookieName;
    /**cookieMaxAge*/
    private Integer cookieMaxAge;

    @PostConstruct
    private void init() {
        // sout
        File pubFile = new File(pubKeyPath);
        File priFile = new File(priKeyPath);

        try {
            if (!pubFile.exists() || !priFile.exists()) {
                // 生成公钥和私钥
                RsaUtils.generateKey(pubKeyPath, priKeyPath, secret);
            }
            privateKey = RsaUtils.getPrivateKey(priKeyPath);
            publicKey = RsaUtils.getPublicKey(pubKeyPath);
        } catch (Exception e) {
            log.error("初始化公钥和私钥失败,{}", e.getCause());
            throw new RuntimeException();
        }

    }
}