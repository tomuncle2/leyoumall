package com.leyou.dao;

import com.leyou.pojo.Category;
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

//    @Select("select * from tb_category where")
//    List<Category> queryCategoryByPid(Long pid);
}