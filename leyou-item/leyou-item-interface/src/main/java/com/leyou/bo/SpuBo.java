package com.leyou.bo;

import com.leyou.pojo.Spu;
import lombok.Data;


import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 15:10 2020/9/9
 * @description: spubo
 */
@Data
public class SpuBo  extends Spu implements Serializable {
    /**商品分类名称*/
    private String cname;
    /**品牌名称*/
    private String bname;
}