package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bo.SpuBo;
import com.leyou.common.page.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.dto.SkuDto;
import com.leyou.dto.SpuDto;
import com.leyou.pojo.Category;
import com.leyou.pojo.Sku;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.pojo.Stock;
import com.leyou.request.SpuRequest;
import com.leyou.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 蔡迪
 * @date: 15:55 2020/9/9
 * @description: 商品serviceimpl
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    /**
     * 后台查询商品列表
     * @date 15:58 2020/9/9
     * @param
     * @return com.leyou.common.page.PageResult<com.leyou.bo.SpuBo>
     */
    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer pageNum, Integer pageSize) {
        Example example = new Example(Spu.class);
        example.orderBy("id").desc();
        Example.Criteria criteria = example.createCriteria();
        if (null != saleable) {
            criteria.andEqualTo("saleable", saleable);
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }
        PageHelper.startPage(pageNum, pageSize);
        List<Spu> list = spuMapper.selectByExample(example);
        PageInfo<Spu> pageInfo = new PageInfo<>(list);
        List<SpuBo> listResult = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            // 分类品牌信息
            List<SpuDto>  list1 = spuMapper.listSpuWithBrandAndCategory(list);
            for (Spu spu : list) {
                SpuBo spuBo = new SpuBo();
                BeanUtils.copyProperties(spu, spuBo);

                for (SpuDto spuDto : list1) {
                    if (spu.getId().equals(spuDto.getId())) {

                        // 品牌信息
                        spuBo.setBname(spuDto.getBname());
                        if (!CollectionUtils.isEmpty(spuDto.getCategories())) {
                            StringBuilder stringBuilder = new StringBuilder();
                            List<Category> categories = spuDto.getCategories();
                            for (int i = 0; i < categories.size(); i++) {
                                stringBuilder.append(categories.get(i).getName());
                                if (i != categories.size() -1) {
                                    stringBuilder.append("/");
                                }
                            }
                            spuBo.setCname(stringBuilder.toString());
                        }
                    }
                }

                // 设置分类名 设置品牌名
                listResult.add(spuBo);
            }
        }

        return new PageResult(pageInfo.getTotal(), listResult);
    }

    /**
     * 新增商品
     * @date 15:59 2020/9/9
     * @param spuRequest
     * @return boolean
     */
    @Override
    @Transactional
    public boolean saveGoods(SpuRequest spuRequest) {
        // 新增spu
        spuRequest.init();
        spuRequest.setLastUpdateTime(spuRequest.getCreateTime());
        spuRequest.setSaleable(true);
        spuRequest.setValid(true);
        int result = spuMapper.insertSelective(spuRequest);

        if (result < 1) {
            return false;
        }
        // 新增spu-detail
        SpuDetail spuDetail = spuRequest.getSpuDetail();
        spuDetail.setSpuId(spuRequest.getId());
        spuDetail.init();

        int resultSpuDetail = spuDetailMapper.insertSelective(spuDetail);

        if (resultSpuDetail < 1) {
            return false;
        }
        // 新增sku

        // 新增库存

        return (saveStockAndSku(spuRequest)) ? true : false;

    }

    /**
     * 修改商品
     * @date 15:59 2020/9/9
     * @param spuRequest
     * @return boolean
     */
    @Override
    public boolean updateGoods(SpuRequest spuRequest) {
        List<SkuDto> list = this.listSkuBySpuId(spuRequest.getId());
        if (!CollectionUtils.isEmpty(list)) {
            List<Long> ids = list.stream().map(s ->s.getId()).collect(Collectors.toList());
            // 删除库存
            Example example = new Example(Stock.class);
            example.createCriteria().andIn("skuId", ids);
            int resultStock = stockMapper.deleteByExample(example);
            if (resultStock < 1) {
                return false;
            }
            Sku sku = new Sku();
            sku.setSpuId(spuRequest.getId());
            // 删除sku
            int resultSku = skuMapper.delete(sku);
            if (resultSku < 1) {
                return false;
            }
        }

        // 新增库存
        saveStockAndSku(spuRequest);

        // 修改spu
        spuRequest.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        spuRequest.setLastUpdateTime(spuRequest.getUpdateTime());
        spuRequest.setValid(null);
        spuRequest.setSaleable(null);
        int result = spuMapper.updateByPrimaryKeySelective(spuRequest);
        if (result < 1) {
            return false;
        }
        // 修改spu-detail
        int resultSpuDetail = spuDetailMapper.updateByPrimaryKeySelective(spuRequest.getSpuDetail());
        if (resultSpuDetail < 1) {
            return false;
        }
        return true;
    }

    /**
     * 新增库存和sku
     * @date 15:11 2020/9/10
     * @param
     * @return boolean
     */
    private boolean saveStockAndSku(SpuRequest spuRequest) {
        //
        List<SkuDto> skus = spuRequest.getSkus();
        // 最好写成批量新增的接口 循环插表不建议
        skus.stream().forEach(skuDto -> {
            // 新增sku
            skuDto.init();
            skuDto.setLastUpdateTime(skuDto.getCreateTime());
            skuDto.setSpuId(spuRequest.getId());
            int result1 = skuMapper.insertSelective(skuDto);
            // 新增库存
            Stock stock = new Stock();
            stock.init();
            stock.setSkuId(skuDto.getId());
            stock.setStock(skuDto.getStock());
            int result2 = stockMapper.insertSelective(stock);
        });
        return true;
    }

    /**
     * spuId获取sku列表
     * @date 17:05 2020/9/10
     * @param spuId
     * @return java.util.List<com.leyou.pojo.Sku>
     */
    @Override
    public List<SkuDto> listSkuBySpuId(Long spuId) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", spuId);
        List<Sku> list = skuMapper.selectByExample(example);
        List<SkuDto> listResult = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach((sku)->{
                // 查询库存
                Example exampleStock = new Example(Stock.class);
                exampleStock.createCriteria().andEqualTo("skuId", sku.getId());
                Stock stock = stockMapper.selectOneByExample(exampleStock);
                SkuDto skuDto = new SkuDto();
                BeanUtils.copyProperties(sku, skuDto);
                skuDto.setStock(stock.getStock());
                listResult.add(skuDto);
            });
        }
        return listResult;
    }

    /**
     * spuId获取SpuDetail列表
     * @date 17:33 2020/9/10
     * @param spuId
     * @return com.leyou.pojo.SpuDetail
     */
    @Override
    public SpuDetail querySpuDetailBySpuId(Long spuId) {
        Example example = new Example(SpuDetail.class);
        example.createCriteria().andEqualTo("spuId", spuId);
        return spuDetailMapper.selectOneByExample(example);
    }

    /**
     * 获取spu
     * @date 17:42 2020/9/10
     * @param id
     * @return com.leyou.pojo.Spu
     */
    @Override
    public Spu querySpuById(Long id) {
        return this.spuMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取sku
     * @date 17:47 2020/9/10
     * @param id
     * @return com.leyou.pojo.Sku
     */
    @Override
    public Sku querySkuById(Long id) {
        return this.skuMapper.selectByPrimaryKey(id);
    }
}