package com.leyou.service;

import com.leyou.common.page.PageResult;
import com.leyou.common.result.Result;
import com.leyou.pojo.Brand;

/**
 * @author: 蔡迪
 * @date: 16:26 2020/9/4
 * @description: 品牌service
 */
public interface BrandService {

    /**
     *分页查询品牌
     * @date 16:54 2020/9/4
     * @param key
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param desc
     * @return org.springframework.http.ResponseEntity<com.leyou.common.page.PageResult<com.leyou.pojo.Brand>>
     */
     PageResult<Brand> queryBrandsByPage(
             String key,
             Integer pageNum,
             Integer pageSize,
              String sortBy,
             Boolean desc);
}