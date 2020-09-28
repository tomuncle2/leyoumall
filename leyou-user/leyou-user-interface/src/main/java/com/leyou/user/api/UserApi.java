package com.leyou.user.api;

import com.leyou.user.pojo.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author: 蔡迪
 * @date: 21:20 2020/9/25
 * @description:
 */

public interface UserApi {

    /**
     * 根据用户名和密码查询用户
     * @date 9:47 2020/9/25
     * @param username
     * @param password
     * @return org.springframework.http.ResponseEntity<User>
     */
    @GetMapping("query")
     User queryUser(
            @RequestParam(value = "type", required = false) Integer type,
            @RequestParam("username") String username,
            @RequestParam("password") String password
    );
}