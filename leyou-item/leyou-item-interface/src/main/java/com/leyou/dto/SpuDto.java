package com.leyou.dto;

import com.leyou.pojo.Category;
import com.leyou.pojo.Spu;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:27 2020/9/9
 * @description:
 */
@Data
public class SpuDto implements Serializable {
    /**spu id*/
    private Long id;
    /**商品分类名称*/
    private List<Category> categories;
    /**品牌名称*/
    private String bname;
}