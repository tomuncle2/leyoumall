package com.leyou.cart;

import com.leyou.cart.config.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author: 蔡迪
 * @date: 18:20 2020/8/15
 * @description:
 */
@SpringBootApplication(scanBasePackages = "com.leyou.*")
@EnableDiscoveryClient
@EnableConfigurationProperties(value = {JwtProperties.class})
@EnableFeignClients
public class CartServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }
}