package com.leyou.service;

import com.leyou.pojo.Category;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 16:43 2020/9/3
 * @description: 分类service
 */
public interface CategoryService {
    /**
     * 父id商品分类查询
     * @date 16:47 2020/9/3
     * @param pid
     * @return java.util.List<com.leyou.pojo.Category>
     */
    List<Category> queryCategoryByPid(Long pid);
}