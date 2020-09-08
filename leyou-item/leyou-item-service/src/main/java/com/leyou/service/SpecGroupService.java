package com.leyou.service;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:15 2020/9/8
 * @description: 规格组service
 */
public interface SpecGroupService {
    /**
     * 获取分类下规格组
     * @date 17:35 2020/9/8
     * @param cid
     * @return java.util.List<com.leyou.pojo.SpecGroup>
     */
    List<SpecGroup> queryGroupsByCid(Long cid);

    /**
     * 新增规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    boolean saveGroup(SpecGroup group);

    /**
     * 修改规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    boolean updateGroup(SpecGroup group);

    /**
     * 删除规格组
     * @date 17:41 2020/9/8
     * @param group
     * @return boolean
     */
    boolean deleteGroup(SpecGroup group);

    /**
     * gid查询规格参数
     * @date 17:42 2020/9/8
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return java.util.List<com.leyou.pojo.SpecParam>
     */
    List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);
}