package com.leyou.cart.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 16:01 2020/9/28
 * @description:
 */
@Data
public class Cart implements Serializable {
    /**用户id*/
    private Long userId;
    /**skuid*/
    private Long skuId;
    /**sku标题*/
    private String title;
    /**image*/
    private String image;
    /**sku主图*/
    private Long price;
    /**商品数量*/
    private Integer num;
    /**特殊规格*/
    private String ownSpec;

}