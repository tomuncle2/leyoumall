package com.leyou.goods.service;

import java.util.Map;

/**
 * @author: 蔡迪
 * @date: 11:21 2020/9/22
 * @description: 页面静态化service
 */
public interface GoodsHtmlService {

    /***
     * 创建模板
     * @date 11:26 2020/9/22
     * @param spuId
     * @return void
     */
     void createHtml(Long spuId);

     /**
      * 创建模板
      * @date 17:13 2020/9/22
      * @param spuMap
      * @return void
      */
     void createHtml(Map<String, Object> spuMap);

     /**
      * 删除
      * @date 17:04 2020/9/22
      * @param id
      * @return void
      */
    void deleteHtml(Long id);
}