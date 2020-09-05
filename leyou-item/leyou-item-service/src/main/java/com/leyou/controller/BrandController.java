package com.leyou.controller;

import com.leyou.common.enums.ResultCodeEnum;
import com.leyou.common.page.PageResult;
import com.leyou.common.result.Result;
import com.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Result<PageResult<Brand>> queryBrandsByPage(
             String key,
            @RequestParam(name = "page", defaultValue = "1") Integer pageNum,
            @RequestParam(name = "rows", defaultValue = "10") Integer pageSize,
            @RequestParam String sortBy,
            @RequestParam Boolean desc){
        return Result.success(brandService.queryBrandsByPage(key, pageNum, pageSize, sortBy, desc));
    }

    /**
     * 创建品牌
     * @date 13:43 2020/9/5
     * @param brand
     * @param cids
     * @return com.leyou.common.result.Result
     */
    @PostMapping
    public Result saveBrand(Brand brand, @RequestParam("cids")List<Long> cids) {
        if (brandService.saveBrand(brand, cids)) {
            return Result.success();
        } else {
            return Result.failure(ResultCodeEnum.OPERATION_FAILED);
        }
    }

    /**
     *
     * @date 16:45 2020/9/5
     * @param id
     * @return com.leyou.common.result.Result
     */
    @GetMapping("/{id}")
    public Result queryBrandById(@PathVariable("id") Long id){
        return Result.success();
    }

    /**
     * 修改品牌
     * @date 16:47 2020/9/5
     * @param brand
     * @param cids
     * @return com.leyou.common.result.Result
     */
    @PutMapping
    public Result updateBrand(Brand brand, @RequestParam("cids")List<Long> cids) {
        return Result.success();
    }
}