package com.leyou.service.impl;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 16:45 2020/9/3
 * @description: 分类实现
 */
@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 父id商品分类查询
     * @date 16:47 2020/9/3
     * @param pid
     * @return java.util.List<com.leyou.pojo.Category>
     */
    @Override
    public List<Category> queryCategoryByPid(Long pid) {
        Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId",pid);
        return categoryMapper.selectByExample(example);
    }

    /**
     *
     * @date 17:02 2020/9/5
     * @param bid
     * @return java.util.List<com.leyou.pojo.Category>
     */
    @Override
    public List<Category> queryCategoryByBid(Long bid) {
        return categoryMapper.queryCategoryByBid(bid);
    }
}