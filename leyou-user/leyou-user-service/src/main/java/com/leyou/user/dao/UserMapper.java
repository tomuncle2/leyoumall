package com.leyou.user.dao;

import com.leyou.user.pojo.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author: 蔡迪
 * @date: 16:26 2020/9/24
 * @description:
 */
@Repository
public interface UserMapper extends Mapper<User> {

}