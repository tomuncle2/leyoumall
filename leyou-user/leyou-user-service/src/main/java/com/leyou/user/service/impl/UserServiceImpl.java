package com.leyou.user.service.impl;

import com.leyou.common.utils.CodecUtils;
import com.leyou.common.utils.NumberUtils;
import com.leyou.common.utils.RedisUtils;
import com.leyou.user.dao.UserMapper;
import com.leyou.user.pojo.User;
import com.leyou.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.concurrent.TimeUnit;


/**
 * @author: 蔡迪
 * @date: 16:29 2020/9/24
 * @description:
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private UserMapper userMapper;

    /**
     * 发送手机验证码
     * @param phone
     * @return org.springframework.http.ResponseEntity
     * @date 17:04 2020/9/24
     */
    @Override
    public boolean sendMessage(String phone) {
        // sout
        String code = NumberUtils.generateCode(6);
        // 发送短信

        // 存入redis
        boolean b = redisUtils.set("code-" + phone, code, 5, TimeUnit.MINUTES);
        if (b) {
            log.info("发送短信成功。phone：{}， code：{}", phone, code);
        } else {
            log.info("发送短信失败。phone：{}， code：{}", phone, code);
        }
        return b;
    }

    /**
     * 校验数据是否可用
     *
     * @param data
     * @param type
     * @return boolean
     * @date 17:04 2020/9/24
     */
    @Override
    public Boolean checkRegisterData(String data, Integer type) {
        User user = new User();
        user.setDeleteMark(1);
        switch (type.intValue()) {
            case 1: // 查询用户;
            user.setUsername(data);
             break;
            case 2: // 检测手机号;
            user.setPhone(data);
            break;
            default: return false;
        }
        int count = userMapper.selectCount(user);
        return count == 0 ;
    }


    /**
     * 注册
     *
     * @param user
     * @param code
     * @return org.springframework.http.ResponseEntity
     * @date 17:03 2020/9/24
     */
    @Override
    public boolean register(User user, String code) {
        // 校验验证码
        Object cacheCode = redisUtils.get("code-" + user.getPhone());
        if (null != cacheCode || StringUtils.equals((String)cacheCode, code)) {
            return false;
        }
        // 生成盐
        String salt = CodecUtils.generateSalt();
       // 对密码加密
        user.setPassword(CodecUtils.md5Hex(user.getPassword(), salt));
        user.init();

        int result = userMapper.insertSelective(user);
        if (result > 0) {
            // 成功删除redis验证码
            redisUtils.del("code-" + user.getPhone());
            return true;
        }
        return false;
    }

    /**
     * 根据用户名和密码查询用户
     * @param type
     * @param username
     * @param password
     * @return com.leyou.user.pojo.User
     * @date 17:03 2020/9/24
     */
    @Override
    public User queryUser(Integer type, String username, String password) {
        // 校验用户名是否存在 phone username email...
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("deleteMark", true);

        Example.Criteria criteriaUsername = example.createCriteria();
        criteriaUsername.orEqualTo("username", username);
        criteriaUsername.orEqualTo("phone", username);

        User user = userMapper.selectOneByExample(example);

        if (null != user) {
           boolean b = StringUtils.equals(user.getPassword(), CodecUtils.md5Hex(password, user.getSalt()));
            if (b) {
                return user;
            }
        }

        return null;
    }
}