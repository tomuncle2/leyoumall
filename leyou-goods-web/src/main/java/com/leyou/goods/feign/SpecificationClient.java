package com.leyou.goods.feign;


import com.leyou.api.SpecificationApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "item-service")
public interface SpecificationClient extends SpecificationApi {
}
