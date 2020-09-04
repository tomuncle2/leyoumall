package com.leyou.pojo;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: 蔡迪
 * @date: 15:53 2020/9/4
 * @description: 品牌
 */
@Data
@Table(name ="tb_brand")
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 品牌名称
    private String name;
    // 品牌图片
    private String image;
    // 首字母
    private Character letter;
}