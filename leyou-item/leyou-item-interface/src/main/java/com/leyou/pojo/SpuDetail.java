package com.leyou.pojo;

import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 15:09 2020/9/9
 * @description: spu详情表
 */
@Data
@Table(name="tb_spu_detail")
public class SpuDetail  extends BasePojo implements Serializable {

    @Id
    /**对应的SPU的id*/
    private Long spuId;
    /**商品描述*/
    private String description;
    /**商品特殊规格的名称及可选值模板*/
    private String specialSpec;
    /**商品的全局规格属性*/
    private String genericSpec;
    /**包装清单*/
    @Column(name = "packing_list")
    private String packingList;
    /**售后服务*/
    private String afterService;
}