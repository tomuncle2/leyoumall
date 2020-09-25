package com.leyou.auth.client;

import com.leyou.user.api.UserApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author: 蔡迪
 * @date: 21:24 2020/9/25
 * @description:
 */
@FeignClient("user-service")
@Component
public interface UserClient extends UserApi {

}