package com.leyou.auth.controller;

import com.leyou.auth.config.JwtConfig;
import com.leyou.auth.dto.UserInfoDTO;
import com.leyou.auth.service.AuthLoginService;
import com.leyou.auth.utils.JwtUtils;
import com.leyou.common.utils.CookieUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author: 蔡迪
 * @date: 21:13 2020/9/25
 * @description: 认证controller
 */
@RestController
@Slf4j
public class AuthLoginController {

    @Autowired
    private AuthLoginService authLoginService;

    @Autowired
    private JwtConfig prop;


    /**
     * 登录认证
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     */
    @PostMapping("accredit")
    public ResponseEntity<Void> AuthLogin(@RequestParam("username") String username,
                                          @RequestParam("password") String password,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        //

        String token = authLoginService.authentication(username, password);
        if (StringUtils.isNotBlank(token)) {
            // token存入cookie
            CookieUtils.setCookie(request, response, prop.getCookieName(),
                    token, prop.getCookieMaxAge(), null, true);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * 获取用户信息
     * @param token
     * @param request
     * @return
     */
    @GetMapping("verify")
    public ResponseEntity<UserInfoDTO> verify(
            @CookieValue(value ="LY_TOKEN", defaultValue = "Atta") String token,
            HttpServletRequest request) {

        // 校验token
        try {
            UserInfoDTO userInfoDTO = JwtUtils.getInfoFromToken(token, prop.getPublicKey());
            return ResponseEntity.ok(userInfoDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
}