package com.leyou.service;

import com.leyou.bo.SpuBo;
import com.leyou.common.page.PageResult;
import com.leyou.dto.SkuDTO;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.request.SpuRequest;

import java.util.List;

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
     * @param spuRequest
     * @return boolean
     */
    boolean saveGoods(SpuRequest spuRequest);

    /**
     * 修改商品
     * @date 15:59 2020/9/9
     * @param spuRequest
     * @return boolean
     */
    boolean updateGoods(SpuRequest spuRequest);

    /**
     * spuId获取sku列表
     * @date 17:05 2020/9/10
     * @param spuId
     * @return java.util.List<com.leyou.pojo.Sku>
     */
    List<SkuDTO> listSkuBySpuId(Long spuId);

    /**
     * spuId获取SpuDetail列表
     * @date 17:29 2020/9/10
     * @param spuId
     * @return java.util.List<com.leyou.pojo.SpuDetail>
     */
    SpuDetail querySpuDetailBySpuId(Long spuId);

    /**
     * 获取spu
     * @date 17:42 2020/9/10
     * @param id
     * @return com.leyou.pojo.Spu
     */
    Spu querySpuById(Long id);

    /**
     * 获取sku
     * @date 17:47 2020/9/10
     * @param id
     * @return com.leyou.pojo.Sku
     */
    Sku querySkuById(Long id);

    /**
     * 上下架
     * @date 10:43 2020/9/11
     * @param id
     * @param saleable
     * @return boolean
     */
     boolean spuSaleableSet(Long id, Boolean saleable);

     /**
      * 删除spu
      * @date 10:44 2020/9/11
      * @param id
      * @return boolean
      */
     boolean spuDelete(Long id);
}