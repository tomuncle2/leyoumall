package com.leyou.goods.feign;


import com.leyou.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {
}
