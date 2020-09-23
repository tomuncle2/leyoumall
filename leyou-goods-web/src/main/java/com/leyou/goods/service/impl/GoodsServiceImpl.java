package com.leyou.goods.service.impl;

import com.leyou.dto.SpecGroupDTO;
import com.leyou.goods.feign.BrandClient;
import com.leyou.goods.feign.CategoryClient;
import com.leyou.goods.feign.GoodsClient;
import com.leyou.goods.feign.SpecificationClient;

import com.leyou.goods.service.GoodsService;
import com.leyou.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: 蔡迪
 * @date: 18:28 2020/9/20
 * @description: 商品详情页impl
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private BrandClient brandClient;

    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private SpecificationClient specificationClient;

    /**
     * 获取商品详情页数据
     * @param spuId
     * @return
     */
    @Override
    public Map<String, Object> loadGoodsDetail(Long spuId) {

        Map<String, Object> resultMap = new TreeMap<>();
        // spu
        Spu spu = goodsClient.querySpuById(spuId).getBody();
        if (null == spu) {
            return new HashMap<>();
        }

        List<Sku> skus = goodsClient.querySkusBySpuId(spuId);

        // spudetail：商品介绍详情页
        SpuDetail spuDetail = goodsClient.querySpuDetailBySpuId(spuId);

        // 分类 List<Map<String, Object>> categories
        List<Long> ids = new ArrayList<>();
        List<String> stringList = categoryClient.queryNamesByIds(Arrays.asList(new Long[]{spu.getCid1(), spu.getCid2(), spu.getCid3()})).getBody();
        List<Map<String, Object>> categories = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", ids.get(i));
            map.put("name", stringList.get(i));
            categories.add(map);
        }

        // brand
         Brand brand = brandClient.queryBrandById(spu.getBrandId());

        // skus:促销标题

        // List<Group> groups：一次性获取组以及组下的规格参数
       List<SpecGroupDTO> specGroupList= specificationClient.querySpecsByCid(spu.getCid3());

        // paramMap:{id,name} 查询特殊的规格参数  为了得到规格id对于的名称   ，id多次查询，合为一次查询所有
        Map<Long, String> paramMap = new HashMap();
        List<SpecParam> specParamGroupList = specificationClient.queryParams(null, spu.getCid3(), false, null).getBody();
        specParamGroupList.stream().forEach((specParam)->{
            paramMap.put(specParam.getId(), specParam.getName());
        });

        resultMap.put("spu", spu);
        resultMap.put("spuDetail", spuDetail);
        resultMap.put("skus", skus);
        resultMap.put("brand", brand);
        resultMap.put("categories", categories);
        resultMap.put("groups", specGroupList);
        resultMap.put("paramMap", paramMap);
        return resultMap;
    }
}