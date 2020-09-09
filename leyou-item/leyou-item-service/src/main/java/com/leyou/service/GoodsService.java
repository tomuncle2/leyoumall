package com.leyou.service;

import com.leyou.bo.SpuBo;
import com.leyou.common.page.PageResult;

/**
 * @author: 蔡迪
 * @date: 15:55 2020/9/9
 * @description: 商品service
 */
public interface GoodsService {

    /**
     * 后台查询商品列表
     * @date 15:58 2020/9/9
     * @param
     * @return com.leyou.common.page.PageResult<com.leyou.bo.SpuBo>
     */
    PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer pageNum, Integer pageSize);

    /**
     * 新增商品
     * @date 15:59 2020/9/9
     * @param spuBo
     * @return boolean
     */
    boolean saveGoods(SpuBo spuBo);

    /**
     * 修改商品
     * @date 15:59 2020/9/9
     * @param spuBo
     * @return boolean
     */
    boolean updateGoods(SpuBo spuBo);
}