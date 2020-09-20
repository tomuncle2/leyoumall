package com.leyou.service.impl;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.dao.SpecParamMapper;
import com.leyou.dto.SpecGroupDTO;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:15 2020/9/8
 * @description: 规格组实现类
 */
@Service
public class SpecGroupServiceImpl implements SpecGroupService {

    @Autowired
    private SpecGroupMapper specGroupMapper;

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * cid获取分类下规格组
     * @date 17:35 2020/9/8
     * @param cid
     * @return java.util.List<com.leyou.pojo.SpecGroup>
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        Example example = new Example(SpecGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cid", cid);
        criteria.andEqualTo("deleteMark", true);
        return specGroupMapper.selectByExample(example);
    }

    /**
     * 获取规格参数组和参数 (商品详情页，商品规格)
     * @param cid
     * @return java.util.List<com.leyou.dto.SpecGroupDTO>
     */
    @Override
    public List<SpecGroupDTO> queryGroupsAndParamsByCid(Long cid) {
        Example example = new Example(SpecGroup.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("cid", cid);
        criteria.andEqualTo("deleteMark", true);
        List<SpecGroup> list = specGroupMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return new ArrayList<>();
        }

        List<SpecGroupDTO> resultList = new ArrayList<>();
        list.stream().forEach(specGroup -> {
            SpecGroupDTO specGroupDTO = new SpecGroupDTO();
            BeanUtils.copyProperties(specGroup, specGroupDTO);
            // 查询组下的参数
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(specGroup.getId());
            specParam.setDeleteMark(1);
            List<SpecParam> specParamList = specParamMapper.select(specParam);
            // 设置
            specGroupDTO.setParams(specParamList);
            resultList.add(specGroupDTO);
        });
        return resultList;
    }

    /**
     * 新增规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    @Override
    public boolean saveGroup(SpecGroup group) {
        group.init();
        int result = specGroupMapper.insertSelective(group);
        return result > 0 ? true : false;
    }

    /**
     * 修改规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    @Override
    public boolean updateGroup(SpecGroup group) {
        group.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int result = specGroupMapper.updateByPrimaryKeySelective(group);
        return result > 0 ? true : false;
    }

    /**
     * 删除规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    @Override
    public boolean deleteGroup(SpecGroup group) {
        group.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        group.setDeleteMark(0);
        int result = specGroupMapper.updateByPrimaryKeySelective(group);
        return result > 0 ? true : false;
    }


}