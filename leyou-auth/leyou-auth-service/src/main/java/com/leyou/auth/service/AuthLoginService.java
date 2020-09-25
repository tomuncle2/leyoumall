package com.leyou.auth.service;

import com.leyou.auth.dto.UserInfoDTO;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 蔡迪
 * @date: 21:15 2020/9/25
 * @description:
 */
public interface AuthLoginService {

    /**
     * 登录认证
     * @param username
     * @param password
     * @return
     */
    String authentication(String username,
                          String password);

//    UserInfoDTO authentication();

}