package com.leyou.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @author: 蔡迪
 * @date: 18:20 2020/8/15
 * @description:
 */
@SpringBootApplication(scanBasePackages = "com.leyou.*")
@EnableEurekaClient
@EnableFeignClients
@MapperScan(basePackages = "com.leyou.user.dao")
public class LeyouUserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeyouUserServiceApplication.class, args);
    }
}