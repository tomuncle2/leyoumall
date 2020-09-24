package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bo.SpuBo;
import com.leyou.common.constant.RabbitMqConstant;
import com.leyou.common.page.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dao.StockMapper;
import com.leyou.dto.SkuDTO;
import com.leyou.dto.SpuDTO;
import com.leyou.pojo.*;
import com.leyou.request.SpuRequest;
import com.leyou.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
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
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private SpuMapper spuMapper;

    @Autowired
    private SpuDetailMapper spuDetailMapper;

    @Autowired
    private SkuMapper skuMapper;

    @Autowired
    private StockMapper stockMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

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
            List<SpuDTO>  list1 = spuMapper.listSpuWithBrandAndCategory(list);
            for (Spu spu : list) {
                SpuBo spuBo = new SpuBo();
                BeanUtils.copyProperties(spu, spuBo);

                for (SpuDTO spuDto : list1) {
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



        // 新增sku 新增库存
        boolean b = saveStockAndSku(spuRequest);
        if (b) {
            // 发送消息到消息队列 ，做商品详情页静态化
            sendMessage(RabbitMqConstant.ITEM_TOPIC_KEY_CHANGE, spuRequest.getId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 修改商品
     * @date 15:59 2020/9/9
     * @param spuRequest
     * @return boolean
     */
    @Override
    public boolean updateGoods(SpuRequest spuRequest) {
        List<SkuDTO> list = this.listSkuBySpuId(spuRequest.getId());
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

        // 新增库存 sku
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

        // 发送消息到消息队列 ，做商品详情页静态化
        sendMessage(RabbitMqConstant.ITEM_TOPIC_KEY_CHANGE, spuRequest.getId());
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
        List<SkuDTO> skus = spuRequest.getSkus();
        // 最好写成批量新增的接口 循环插表不建议
        skus.stream().forEach(skuDTO -> {
            // 新增sku
            skuDTO.init();
            skuDTO.setLastUpdateTime(skuDTO.getCreateTime());
            skuDTO.setSpuId(spuRequest.getId());
            int result1 = skuMapper.insertSelective(skuDTO);
            // 新增库存
            Stock stock = new Stock();
            stock.init();
            stock.setSkuId(skuDTO.getId());
            stock.setStock(skuDTO.getStock());
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
    public List<SkuDTO> listSkuBySpuId(Long spuId) {
        Example example = new Example(Sku.class);
        example.createCriteria().andEqualTo("spuId", spuId);
        List<Sku> list = skuMapper.selectByExample(example);
        List<SkuDTO> listResult = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().forEach((sku)->{
                // 查询库存
                Example exampleStock = new Example(Stock.class);
                exampleStock.createCriteria().andEqualTo("skuId", sku.getId());
                Stock stock = stockMapper.selectOneByExample(exampleStock);
                SkuDTO skuDto = new SkuDTO();
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

    /**
     * 上下架
     * @date 10:43 2020/9/11
     * @param id
     * @param saleable
     * @return boolean
     */
    @Override
    public boolean spuSaleableSet(Long id, Boolean saleable) {
        Spu spu = new Spu();
        spu.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        spu.setId(id);
        if (null != saleable) {
            spu.setSaleable(saleable);
        }
        int resultU = spuMapper.updateByPrimaryKeySelective(spu);
        return resultU > 0 ? true : false;
    }

    /**
     * 删除spu
     * @date 10:44 2020/9/11
     * @param id
     * @return boolean
     */
    @Override
    public boolean spuDelete(Long id) {
        Spu spu = new Spu();
        spu.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        spu.setId(id);
        spu.setValid(false);
        spu.setDeleteMark(0);
        int resultU = spuMapper.updateByPrimaryKeySelective(spu);
        if( resultU > 0 ) {
            sendMessage(RabbitMqConstant.ITEM_TOPIC_KEY_DELETE, id);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 发送消息
     * @date 17:57 2020/9/22
     * @param key
     * @param id
     * @return void
     */
    public void sendMessage(String key, Long id) {
        // 发送消息到消息队列 ，做商品详情页静态化
        try {
            amqpTemplate.convertAndSend(RabbitMqConstant.ITEM_EXCHANGE_TOPIC,key, id);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("[item-service]=== title  出现异常 {}", e.getMessage());
        }
    }
}