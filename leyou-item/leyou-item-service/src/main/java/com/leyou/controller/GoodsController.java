package com.leyou.controller;

import com.leyou.bo.SpuBo;
import com.leyou.common.page.PageResult;
import com.leyou.service.GoodsService;
import com.leyou.service.impl.GoodsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蔡迪
 * @date: 15:53 2020/9/9
 * @description: 商品controller
 */
@RestController
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer pageSize) {
        PageResult<SpuBo> pageResult = goodsService.querySpuBoByPage( key,  saleable,  pageNum,  pageSize);
        return ResponseEntity.ok(pageResult);
    }
}