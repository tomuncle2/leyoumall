package com.leyou.controller;

import com.leyou.common.enums.ResultCodeEnum;
import com.leyou.common.page.PageResult;
import com.leyou.common.result.Result;
import com.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 16:27 2020/9/4
 * @description: 品牌controller
 */
@RestController
@Slf4j
@RequestMapping("brand")
public class BrandController {

    @Autowired
    private BrandService brandService;

    /**
     * 后台管理-品牌分页查询
     * @date 16:41 2020/9/4
     * @param key
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param desc
     * @return org.springframework.http.ResponseEntity<com.leyou.common.page.PageResult<com.leyou.pojo.Brand>>
     */
    @GetMapping("page")
    public ResponseEntity<PageResult<Brand>> queryBrandsByPage(
             String key,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "rows", defaultValue = "10") Integer pageSize,
            @RequestParam String sortBy,
            @RequestParam Boolean desc){
        return ResponseEntity.ok(brandService.queryBrandsByPage(key, pageNum, pageSize, sortBy, desc));
    }

    /**
     * 创建品牌
     * @date 13:43 2020/9/5
     * @param brand
     * @param cids
     * @return com.leyou.common.result.Result
     */
    @PostMapping
    public ResponseEntity saveBrand(Brand brand, @RequestParam("cids")List<Long> cids) {
        if (brandService.saveBrand(brand, cids)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
        }
    }

    /**
     *
     * @date 16:45 2020/9/5
     * @param id
     * @return com.leyou.common.result.Result
     */
    @GetMapping("/{id}")
    public ResponseEntity<Brand> queryBrandById(@PathVariable("id") Long id){
        return ResponseEntity.ok(brandService.queryBrandById(id));
    }

    /**
     * 修改品牌
     * @date 16:47 2020/9/5
     * @param brand
     * @param cids
     * @return com.leyou.common.result.Result
     */
    @PutMapping
    public ResponseEntity updateBrand(Brand brand, @RequestParam("cids")List<Long> cids) {
        if (brandService.updateBrand(brand, cids)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
        }
    }
}