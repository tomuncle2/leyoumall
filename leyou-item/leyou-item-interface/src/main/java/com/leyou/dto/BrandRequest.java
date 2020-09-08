package com.leyou.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 15:32 2020/9/8
 * @description:
 */
@Data
public class BrandRequest implements Serializable {
    // 品牌id
    private Long id;
    // 品牌名称
    private String name;
    // 品牌图片
    private String image;
    // 首字母
    private Character letter;
}