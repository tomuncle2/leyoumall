package com.leyou.service;

import com.leyou.common.page.PageResult;
import com.leyou.common.result.Result;
import com.leyou.dto.BrandRequest;
import com.leyou.pojo.Brand;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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


     /**
      * 新增品牌
      * @date 11:15 2020/9/5
      * @param brand
      * @param cids
      * @return void
      */
     boolean saveBrand(Brand brand, List<Long> cids);

     /**
      * 品牌id查询详情
      * @date 16:51 2020/9/5
      * @param id
      * @return com.leyou.pojo.Brand
      */
     Brand queryBrandById(Long id);

    /**
     * 修改品牌
     * @date 16:47 2020/9/5
     * @param request
     * @param cids
     * @return com.leyou.common.result.Result
     */
    boolean updateBrand(BrandRequest request, List<Long> cids);

    /**
     * 删除品牌
     * @date 10:47 2020/9/8
     * @param bid
     * @return boolean
     */
    boolean deleteCategoryByBid(Long bid);
}