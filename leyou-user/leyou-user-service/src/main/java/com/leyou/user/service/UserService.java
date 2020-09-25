package com.leyou.user.service;

import com.leyou.user.pojo.User;


/**
 * @author: 蔡迪
 * @date: 16:29 2020/9/24
 * @description:
 */
public interface UserService {

    /**
     * 发送手机验证码
     *
     * @param phone
     * @return org.springframework.http.ResponseEntity
     * @date 17:04 2020/9/24
     */
    boolean sendMessage(String phone);

    /**
     * 校验数据是否可用
     *
     * @param data
     * @param type
     * @return boolean
     * @date 17:04 2020/9/24
     */
    Boolean checkRegisterData(String data, Integer type);


    /**
     * 注册
     *
     * @param user
     * @param code
     * @return org.springframework.http.ResponseEntity
     * @date 17:03 2020/9/24
     */
    boolean register(User user, String code);

    /**
     * 根据用户名和密码查询用户
     * @param type
     * @param username
     * @param password
     * @return User
     * @date 17:03 2020/9/24
     */
    User queryUser(Integer type, String username, String password);
}