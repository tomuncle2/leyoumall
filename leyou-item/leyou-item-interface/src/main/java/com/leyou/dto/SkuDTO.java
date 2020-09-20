package com.leyou.dto;

import com.leyou.pojo.Sku;
import lombok.Data;
import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 15:12 2020/9/9
 * @description: sku
 */
@Data
public class SkuDTO extends Sku implements Serializable {

    /**库存*/
    private Integer stock;
}