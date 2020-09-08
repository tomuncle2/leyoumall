package com.leyou.service.impl;

import com.leyou.dao.SpecGroupMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * 获取分类下规格组
     * @date 17:35 2020/9/8
     * @param cid
     * @return java.util.List<com.leyou.pojo.SpecGroup>
     */
    @Override
    public List<SpecGroup> queryGroupsByCid(Long cid) {
        return null;
    }

    /**
     * 新增规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    @Override
    public boolean saveGroup(SpecGroup group) {
        return false;
    }

    /**
     * 修改规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    @Override
    public boolean updateGroup(SpecGroup group) {
        return false;
    }

    /**
     * 删除规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    @Override
    public boolean deleteGroup(SpecGroup group) {
        return false;
    }

    /**
     * gid查询规格参数
     * @date 17:42 2020/9/8
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return java.util.List<com.leyou.pojo.SpecParam>
     */
    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        return null;
    }
}