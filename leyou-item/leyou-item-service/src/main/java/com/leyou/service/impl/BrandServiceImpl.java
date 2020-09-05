package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.page.PageResult;
import com.leyou.dao.BrandMapper;
import com.leyou.pojo.Brand;
import com.leyou.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 16:26 2020/9/4
 * @description: 品牌实现类
 */
@Service
@Slf4j
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandMapper brandMapper;

    /**
     *分页查询品牌
     * @date 16:54 2020/9/4
     * @param key
     * @param pageNum
     * @param pageSize
     * @param sortBy
     * @param desc
     * @return org.springframework.http.ResponseEntity<com.leyou.common.page.PageResult<com.leyou.pojo.Brand>>
     */
    @Override
    public PageResult<Brand> queryBrandsByPage(String key, Integer pageNum, Integer pageSize, String sortBy, Boolean desc) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();

        // 排序
        String orderCase = " " + sortBy + (desc ? " asc " : " desc ");
        example.setOrderByClause(orderCase);
        // 品牌首字母或者品牌名查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("name","%" + key + "%").orEqualTo("letter",key);
        }

        PageHelper.startPage(pageNum, pageSize, true);
        List<Brand> list = brandMapper.selectByExample(example);
        PageInfo<Brand> pageInfo = new PageInfo<>(list);
        PageResult<Brand> pageResult = new PageResult(pageInfo.getTotal(), pageInfo.getPageNum(), list);
        // 分页
        return pageResult;
    }

    /**
     * 新增品牌
     * @date 11:15 2020/9/5
     * @param brand
     * @param cids
     * @return void
     */
    @Override
    @Transactional
    public boolean saveBrand(Brand brand, List<Long> cids) {
        int result = brandMapper.insertSelective(brand);
        if (result < 1) {
            return false;
        }
        cids.stream().forEach((cid)-> {
            brandMapper.saveCategoryBrand(brand.getId(), cid);
        });
        return true;
    }

    /**
     * 品牌id查询详情
     * @date 16:51 2020/9/5
     * @param id
     * @return com.leyou.pojo.Brand
     */
    @Override
    public Brand queryBrandById(Long id) {
        Example example = new Example(Brand.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("id", id);
        return brandMapper.selectOneByExample(example);
    }

    /**
     * 修改品牌
     * @date 16:47 2020/9/5
     * @param brand
     * @param cids
     * @return com.leyou.common.result.Result
     */
    @Override
    public boolean updateBrand(Brand brand, List<Long> cids) {
        int result = brandMapper.updateByPrimaryKey(brand);
        if (result < 1) {
            return false;
        }

        return true;
    }
}