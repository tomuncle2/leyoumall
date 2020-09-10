package com.leyou.request;

import com.leyou.dto.SkuDto;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 14:31 2020/9/10
 * @description:
 */
@Data
public class SpuRequest extends Spu implements Serializable {

    /**spu详情*/
    private SpuDetail spuDetail;

    /**sku列表*/
    private List<SkuDto> skus;
}