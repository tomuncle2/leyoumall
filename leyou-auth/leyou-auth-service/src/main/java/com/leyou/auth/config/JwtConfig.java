package com.leyou.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author: 蔡迪
 * @date: 17:48 2020/9/25
 * @description: jwt 参数配置类
 */
@ConfigurationProperties(prefix = "leyou.jwt")
@Data
public class JwtConfig {
    private String secret;

    private String pubKeyPath;// 公钥

    private String priKeyPath;// 私钥

    private int expire;// token过期时间

    private PublicKey publicKey; // 公钥

    private PrivateKey privateKey; // 私钥

    private String cookieName;

    private Integer cookieMaxAge;
}