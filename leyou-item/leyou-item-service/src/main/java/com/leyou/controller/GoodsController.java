package com.leyou.controller;

import com.leyou.bo.SpuBo;
import com.leyou.common.page.PageResult;
import com.leyou.dto.SkuDTO;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.request.SpuRequest;
import com.leyou.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 15:53 2020/9/9
 * @description: 商品controller
 */
@RestController
@Slf4j
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<SpuBo>> querySpuBoByPage(
            @RequestParam(value = "key", required = false) String key,
            @RequestParam(value = "saleable", required = false) Boolean saleable,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "rows", required = false, defaultValue = "10") Integer pageSize) {
        PageResult<SpuBo> pageResult = goodsService.querySpuBoByPage( key,  saleable,  pageNum,  pageSize);
        return ResponseEntity.ok(pageResult);
    }

    /**
     * 新增商品
     * @date 15:37 2020/9/10
     * @param spuRequest
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping("goods")
    public ResponseEntity saveGoods(@RequestBody SpuRequest spuRequest)  {
        if (goodsService.saveGoods(spuRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
    }

    /**
     * 修改商品
     * @date 15:37 2020/9/10
     * @param spuRequest
     * @return org.springframework.http.ResponseEntity
     */
    @PutMapping("goods")
    public ResponseEntity updateGoods(@RequestBody SpuRequest spuRequest)  {
        if (goodsService.updateGoods(spuRequest)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
    }

    /**
     * spuId获取sku列表
     * @date 17:50 2020/9/10
     * @param id
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.dto.SkuDTO>>
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<SkuDTO>> listSkuBySpuId(@RequestParam("id") Long id) {
        return ResponseEntity.ok(goodsService.listSkuBySpuId(id));
    }

    /**
     * spuId获取SpuDetail列表
     * @date 17:50 2020/9/10
     * @param spuId
     * @return org.springframework.http.ResponseEntity<com.leyou.pojo.SpuDetail>
     */
    @GetMapping("spu/detail/{spuId}")
    public ResponseEntity<SpuDetail> getSpuDetail(@PathVariable("spuId") Long spuId) {
        return ResponseEntity.ok(goodsService.querySpuDetailBySpuId(spuId));
    }



    /**
     * 获取sku
     * @date 17:49 2020/9/10
     * @param id
     * @return org.springframework.http.ResponseEntity<com.leyou.pojo.Sku>
     */
    @GetMapping("sku/{id}")
    public ResponseEntity<Sku> querySkuById(@PathVariable("id")Long id){
        return ResponseEntity.ok(goodsService.querySkuById(id));
    }


    /**
     * 上下架
     * @date 10:54 2020/9/11
     * @param id
     * @param saleable
     * @return org.springframework.http.ResponseEntity
     */
    @PutMapping("goods/saleable/{id}")
    public ResponseEntity spuSaleableSet(@PathVariable("id") Long id, @RequestParam("saleable") Boolean saleable) {
        if (goodsService.spuSaleableSet(id, saleable)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
    }

    /**
     * 删除spu
     * @date 10:54 2020/9/11
     * @param id
     * @return org.springframework.http.ResponseEntity
     */
    @DeleteMapping("goods/delete/{id}")
    public ResponseEntity spuDelete(@PathVariable("id") Long id) {
        if (goodsService.spuDelete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.METHOD_FAILURE).build();
    }

    /**********************************前台门户接口***************************************/
    /**
     * 获取spu
     * @date 17:50 2020/9/10
     * @param id
     * @return org.springframework.http.ResponseEntity<com.leyou.pojo.Spu>
     */
    @GetMapping("spu/{id}")
    public ResponseEntity<Spu>  querySpuById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(goodsService.querySpuById(id));
    }

}