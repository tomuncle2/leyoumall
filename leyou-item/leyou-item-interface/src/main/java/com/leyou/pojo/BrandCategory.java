package com.leyou.pojo;



import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: 蔡迪
 * @date: 11:03 2020/9/8
 * @description: 品牌-分类关联表
 */
@Table(name = "tb_category_brand")
@Data
public class BrandCategory  extends BasePojo {
    @Id
    @Column(name= "category_id")
    private Long categoryId;

    @Id
    @Column(name= "brand_id")
    private Long brandId;
}