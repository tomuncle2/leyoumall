package com.leyou.service;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:13 2020/9/8
 * @description: 规格service
 */
public interface SpecParamService {

    /**
     * gid查询规格参数(前台搜索和后台管理共用接口 实际开发不建议这么使用)
     * @date 17:42 2020/9/8
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return java.util.List<com.leyou.pojo.SpecParam>
     */
     List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching);

     List<SpecGroup> querySpecsByCid(Long cid);

     /**
      * 新增规格参数
      * @date 10:42 2020/9/9
      * @param param
      * @return boolean
      */
    boolean saveParam(SpecParam param);

    /**
     * 修改规格参数
     * @date 10:42 2020/9/9
     * @param param
     * @return boolean
     */
    boolean updateParam(SpecParam param);

    /**
     * 删除规格参数
     * @date 10:42 2020/9/9
     * @param param
     * @return boolean
     */
    boolean deleteParam(SpecParam param);
}