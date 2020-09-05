package com.leyou.dao;

import com.leyou.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;


/**
 * @author: 蔡迪
 * @date: 16:49 2020/9/3
 * @description: 商品分类mapper
 */
@Repository
public interface CategoryMapper extends Mapper<Category> {

    /**
     * 品牌id查询分类
     * @date 17:04 2020/9/5
     * @param bid
     * @return java.util.List<com.leyou.pojo.Category>
     */
    @Select("select id,name,parent_id as parentId,is_parent as isParent,sort from tb_category where id in (select category_id from tb_category_brand where brand_id = #{bid})")
    List<Category> queryCategoryByBid(@Param("bid") Long bid);
}