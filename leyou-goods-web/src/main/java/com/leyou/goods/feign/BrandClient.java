package com.leyou.goods.feign;


import com.leyou.api.BrandApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("item-service")
public interface BrandClient extends BrandApi {
}