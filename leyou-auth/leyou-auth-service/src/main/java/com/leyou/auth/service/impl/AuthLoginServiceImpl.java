package com.leyou.auth.service.impl;

import com.leyou.auth.client.UserClient;
import com.leyou.auth.config.JwtConfig;
import com.leyou.auth.dto.UserInfoDTO;
import com.leyou.auth.service.AuthLoginService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.user.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 蔡迪
 * @date: 21:15 2020/9/25
 * @description:
 */
@Service
@Slf4j
public class AuthLoginServiceImpl implements AuthLoginService {

    @Autowired
    private UserClient userClient;

    @Autowired
    private JwtConfig jwtConfig;
    /**
     * 登录认证
     * @param username
     * @param password
     * @return
     */
    @Override
    public String authentication(String username, String password) {

        User user  = userClient.queryUser(null, username, password);
        if (null != user) {
            try {
                String token = JwtUtils.generateToken(new UserInfoDTO(user.getId(), user.getUsername()), jwtConfig.getPrivateKey(),jwtConfig.getExpire());
                return token;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}