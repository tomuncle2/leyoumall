package com.leyou.goods.controller;

import com.leyou.goods.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

/**
 * @author: 蔡迪
 * @date: 15:06 2020/9/19
 * @description: 商品详情页微服务
 */
@Controller
@RequestMapping("item")
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService service;

    /**
     * 跳转商品详情页
     * @date 17:05 2020/9/19
     * @param model
     * @param id
     * @return java.lang.String
     */
    @GetMapping("{id}.html")
    public String toItemDetail(Model model, @PathVariable("id") Long id) {
        // sout
        Map<String, Object> map = service.loadGoodsDetail(id);
        model.addAllAttributes(map);
        return "item";
    }
}