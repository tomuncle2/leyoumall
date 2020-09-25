package com.leyou.auth.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 载荷对象
 */
@Data
public class UserInfoDTO implements Serializable {

    private Long id;

    private String username;

    public UserInfoDTO() {
    }

    public UserInfoDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

}