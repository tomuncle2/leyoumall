package com.leyou.dao;

import com.leyou.dto.SpuDTO;
import com.leyou.pojo.Spu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 15:46 2020/9/9
 * @description: 商品mapper
 */
@Repository
public interface SpuMapper extends Mapper<Spu> {

    /**
     * 获取spu品牌和分类信息名称信息
     * @date 17:51 2020/9/9
     * @param list
     * @return java.util.List<com.leyou.dto.SpuDTO>
     */
    List<SpuDTO> listSpuWithBrandAndCategory(@Param("list") List<Spu> list);
}