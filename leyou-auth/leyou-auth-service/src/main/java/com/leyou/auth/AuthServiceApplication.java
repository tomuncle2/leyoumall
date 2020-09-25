package com.leyou.auth;

import com.leyou.auth.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author: 蔡迪
 * @date: 18:20 2020/8/15
 * @description:
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableConfigurationProperties(value = {JwtConfig.class})
public class AuthServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }
}