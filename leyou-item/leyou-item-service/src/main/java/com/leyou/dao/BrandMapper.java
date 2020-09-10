package com.leyou.dao;

import com.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 16:25 2020/9/4
 * @description: 品牌mapper
 */
@Repository
public interface BrandMapper extends Mapper<Brand> {

    /**
     * 品牌商品中间表
     * @date 13:37 2020/9/5
     * @param bid
     * @param cid
     * @return int
     */
    @Insert("insert into tb_category_brand (brand_id,category_id) values (#{bid}, #{cid})")
    int saveCategoryBrand(@Param("bid")Long bid, @Param("cid")Long cid);

    @Select("   select \n" +
            "\t id,\n" +
            "\t name,\n" +
            "\t image,\n" +
            "\t letter\n" +
            "\tfrom  tb_brand where id in (\n" +
            "\t SELECT brand_id FROM `tb_category_brand` where category_id = #{cid}\n" +
            "\t )")
    List<Brand> listBrandByCid(@Param("cid")Long cid);
}