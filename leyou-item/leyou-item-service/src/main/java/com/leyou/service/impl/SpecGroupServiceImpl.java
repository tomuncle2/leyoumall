package com.leyou.service.impl;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
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