package com.leyou.service.impl;

import com.leyou.dao.CategoryMapper;
import com.leyou.pojo.Category;
import com.leyou.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
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
        criteria.andEqualTo("deleteMark", true);
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

    /**
     * 新增分类
     * @date 14:05 2020/9/9
     * @param category
     * @return boolean
     */
    @Override
    @Transactional
    public boolean saveCategory(Category category) {
        // 新增子节点
        category.init();
        int result = categoryMapper.insertSelective(category);
        if (result < 1) {
            return false;
        }
        // 更新父节点信息
        Category categoryP = new Category();
        categoryP.setId(category.getParentId());
        categoryP.setIsParent(true);
        category.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int resultU = categoryMapper.updateByPrimaryKeySelective(category);
        return resultU > 0 ? true : false;
    }

    /**
     * 修改分类
     * @date 14:05 2020/9/9
     * @param category
     * @return boolean
     */
    @Override
    public boolean updateCategory(Category category) {
        category.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int result = categoryMapper.updateByPrimaryKeySelective(category);
        return result > 0 ? true : false;
    }

    /**
     * 删除分类
     * @date 14:05 2020/9/9
     * @param category
     * @return boolean
     */
    @Override
    public boolean deleteCategory(Category category) {
        // 不是父节点 ，直接删除， 以及品牌-分类关联表信息

        // 是父节点不允许删除，需要先删除子节点
        return false;
    }

    /**
     * 查询分类名称
     * */
    @Override
    public List<String> queryNamesByIds(List<Long> ids) {
        List<Category> list = this.categoryMapper.selectByIdList(ids);
        List<String> resultList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach((category)->{
                resultList.add(category.getName());
            });
        }
        return resultList;
    }
}