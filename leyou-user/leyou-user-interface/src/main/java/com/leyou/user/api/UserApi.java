package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 蔡迪
 * @date: 21:20 2020/9/25
 * @description:
 */
@RequestMapping("/")
public interface UserApi {

    /**
     * 根据用户名和密码查询用户
     * @date 9:47 2020/9/25
     * @param username
     * @param password
     * @param type
     * @return org.springframework.http.ResponseEntity<User>
     */
    @GetMapping("query")
    ResponseEntity<User> queryUser(@RequestParam("username") String username,
                                          @RequestParam("password") String password,
                                          Integer type);
}