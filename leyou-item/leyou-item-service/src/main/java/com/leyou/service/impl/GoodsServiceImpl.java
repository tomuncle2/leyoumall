package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.bo.SpuBo;
import com.leyou.common.page.PageResult;
import com.leyou.dao.SkuMapper;
import com.leyou.dao.SpuDetailMapper;
import com.leyou.dao.SpuMapper;
import com.leyou.dto.SpuDto;
import com.leyou.pojo.Category;
import com.leyou.pojo.Spu;
import com.leyou.pojo.SpuDetail;
import com.leyou.service.GoodsService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 后台查询商品列表
     * @date 15:58 2020/9/9
     * @param
     * @return com.leyou.common.page.PageResult<com.leyou.bo.SpuBo>
     */
    @Override
    public PageResult<SpuBo> querySpuBoByPage(String key, Boolean saleable, Integer pageNum, Integer pageSize) {
        Example example = new Example(Spu.class);
        example.orderBy("id").asc();
        Example.Criteria criteria = example.createCriteria();
        if (null != saleable) {
            criteria.andEqualTo("saleable", saleable);
        }
        if (StringUtils.isNotBlank(key)) {
            criteria.andEqualTo("title", "%" + key + "%");
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
                                stringBuilder.append(categories.get(i));
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

    @Override
    public boolean saveGoods(SpuBo spuBo) {
        return false;
    }

    @Override
    public boolean updateGoods(SpuBo spuBo) {
        return false;
    }
}