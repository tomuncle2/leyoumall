package com.leyou;

import com.leyou.config.JwtProperties;
import com.leyou.config.WhiteListProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author: 蔡迪
 * @date: 16:56 2020/8/15
 * @description:
 */
@SpringBootApplication
@EnableEurekaClient
@EnableZuulProxy
@EnableConfigurationProperties(value = {JwtProperties.class, WhiteListProperties.class})
public class LeyouGateWayApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouGateWayApplication.class, args);
    }
}