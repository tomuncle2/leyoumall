package com.leyou.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.page.PageResult;
import com.leyou.dao.BrandCategoryMapper;
import com.leyou.dao.BrandMapper;
import com.leyou.dao.CategoryMapper;
import com.leyou.dto.BrandRequest;
import com.leyou.pojo.Brand;
import com.leyou.pojo.BrandCategory;
import com.leyou.service.BrandService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
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

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private BrandCategoryMapper brandCategoryMapper;
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
        criteria.andEqualTo("deleteMark", true);
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
        brand.init();
        int result = brandMapper.insertSelective(brand);
        if (result < 1) {
            return false;
        }
        // 批量新增
        cids.stream().forEach((cid)-> {
            BrandCategory c = new BrandCategory();
            c.init();
            c.setBrandId(brand.getId());
            c.setCategoryId(cid);
            brandCategoryMapper.insertSelective(c);
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
        criteria.andEqualTo("id", id);
        criteria.andEqualTo("deleteMark", true);
        return brandMapper.selectOneByExample(example);
    }

    /**
     * 修改品牌
     * @date 16:47 2020/9/5
     * @param request
     * @param cids
     * @return com.leyou.common.result.Result
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateBrand(BrandRequest request, List<Long> cids) {
        Brand brand = new Brand();
        brand.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        BeanUtils.copyProperties(request, brand);
        int result = brandMapper.updateByPrimaryKeySelective(brand);
        if (result < 1) {
            return false;
        }
        // 修改分类信息
        int result2 = categoryMapper.deleteCategoryByBid(brand.getId());
        cids.stream().forEach((cid)->{
            BrandCategory c = new BrandCategory();
            c.init();
            c.setBrandId(brand.getId());
            c.setCategoryId(cid);
            brandCategoryMapper.insertSelective(c);
        });
        return true;
    }

    /**
     * 删除品牌
     * @date 10:47 2020/9/8
     * @param bid
     * @return boolean
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteCategoryByBid(Long bid) {
        Brand brand = new Brand();
        brand.setId(bid);
        brand.setDeleteMark(0);
        int result = brandMapper.updateByPrimaryKeySelective(brand);

        if (result > 0) {
            // 删除中间表
            int result2 = categoryMapper.deleteCategoryByBid(bid);
            return result2 < 1 ? false : true;
        }

        return false;
    }

    /**
     * 分类id查询品牌列表（商品新增编辑用）
     * @date 10:59 2020/9/10
     * @param cid
     * @return java.util.List<com.leyou.pojo.Brand>
     */
    @Override
    public List<Brand> queryBrandsByCid(Long cid) {
        return brandMapper.listBrandByCid(cid);
    }
}