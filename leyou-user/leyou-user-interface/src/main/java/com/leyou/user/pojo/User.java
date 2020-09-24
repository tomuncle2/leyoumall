package com.leyou.user.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.leyou.common.pojo.BasePojo;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @author: 蔡迪
 * @date: 16:12 2020/9/24
 * @description:
 */
@Data
@Table(name = "tb_user")
public class User extends BasePojo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Length(min = 4, max = 30, message = "用户名只能在4~30位之间")
    private String username;

    /**序列化的时候隐藏*/
    @JsonIgnore
    @Length(min = 4, max = 30, message = "密码只能在4~30位之间")
    private String password;

    @Pattern(regexp = "^1[35678]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    private Date created;

    /**序列化的时候隐藏*/
    @JsonIgnore
    private String salt;
}