package com.leyou.user.controller;

import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
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
@Controller
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @PostMapping("send")
    public ResponseEntity sendMessage(String phone) {
        if (userService.sendMessage(phone)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
        }
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
        Boolean boo = userService.checkRegisterData(data, type);
        if (boo == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(boo);
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
        if (userService.register(user, code)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
        }
    }

    /**
     * 根据用户名和密码查询用户
     * @date 9:47 2020/9/25
     * @param username
     * @param password
     * @param type
     * @return org.springframework.http.ResponseEntity<User>
     */
    @GetMapping("query")
    public ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password,
                                          Integer type) {
        return ResponseEntity.ok(userService.queryUser(type,username, password));
    }
}