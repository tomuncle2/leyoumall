package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蔡迪
 * @date: 16:28 2020/9/24
 * @description:
 */
@RestController
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("code")
    public ResponseEntity sendMessage(String phone) {

        return null;
    }

    /**
     * 校验数据是否可用
     * @date 16:58 2020/9/24
     * @param data
     * @param type
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping("check/{data}/{type}")
    public ResponseEntity checkRegisterData(@PathVariable("data") String data, @PathVariable("type")Integer type) {
        //
        return null;
    }

    /**
     * 注册
     * @date 17:00 2020/9/24
     * @param user
     * @param code
     * @return org.springframework.http.ResponseEntity
     */
    @GetMapping("register")
    public ResponseEntity register(@Validated User user, @RequestParam("code")String code) {
        //

        return null;
    }

    /**
     * 根据用户名和密码查询用户
     * @param username
     * @param password
     * @return
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password) {
        return null;
    }
}