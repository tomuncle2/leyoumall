package com.leyou.goods.service;

import java.util.Map;

/**
 * @author: 蔡迪
 * @date: 18:27 2020/9/20
 * @description: 商品详情页service
 */
public interface GoodsService {

    /**
     * 获取商品详情页数据
     * @param spuId
     * @return
     */
    Map<String, Object> loadGoodsDetail(Long spuId);
}