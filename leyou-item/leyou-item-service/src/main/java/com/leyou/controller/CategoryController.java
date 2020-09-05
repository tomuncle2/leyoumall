package com.leyou.controller;

import com.leyou.common.result.Result;
import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 16:17 2020/9/3
 * @description: 分类controller
 */
@Slf4j
@RequestMapping("category")
@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    /**
     * 父id获取分类
     * @date 16:41 2020/9/3
     * @param pid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.pojo.Category>>
     */
    @GetMapping("/list")
    public ResponseEntity<List<Category>> queryCategoryByPid(@RequestParam("pid") Long pid) {
        log.info("category/list");
        if (StringUtils.isEmpty(pid) || pid.intValue() < 0) {
            return ResponseEntity.badRequest().build();

        }

        List<Category> list = categoryService.queryCategoryByPid(pid);
        if (CollectionUtils.isEmpty(list)) {
            //没有找到返回404
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } else {
            //找到返回200
            return ResponseEntity.ok(list);
        }
    }

    /**
     * 品牌id查询所有分类
     * @date 16:49 2020/9/5
     * @param bid
     * @return com.leyou.common.result.Result
     */
    @GetMapping("bid/{bid}")
    public Result queryCategoryByBid(@RequestParam("bid")Long bid) {
        return Result.success(categoryService.queryCategoryByBid(bid));
    }
}