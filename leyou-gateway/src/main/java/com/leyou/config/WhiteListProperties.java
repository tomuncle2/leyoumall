package com.leyou.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 22:53 2020/9/25
 * @description: 白名单
 */
@Slf4j
@ConfigurationProperties(prefix = "leyou.filter")
@Data
public class WhiteListProperties {

    // 白名单
    private List<String> allowPaths;
}