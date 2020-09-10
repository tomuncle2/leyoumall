package com.leyou.pojo;

import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 蔡迪
 * @date: 15:12 2020/9/9
 * @description: sku
 */
@Data
@Table(name = "tb_sku")
public class Sku extends BasePojo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long spuId;

    private String title;

    private String images;

    private Long price;

    /**商品值多变规格的键值对*/
    private String ownSpec;

    /**商品特殊规格的下标,_隔开*/
    private String indexes;

    /**是否有效，逻辑删除用*/
    private Boolean enable;

    /**最后修改时间*/
    private Timestamp lastUpdateTime;
}