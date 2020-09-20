package com.leyou.service.impl;

import com.leyou.dao.SpecParamMapper;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:14 2020/9/8
 * @description: 规格实现类
 */
@Service
public class SpecParamServiceImpl implements SpecParamService {

    @Autowired
    private SpecParamMapper specParamMapper;

    /**
     * gid查询规格参数(前台搜索和后台管理共用接口 实际开发不建议这么使用)
     * @date 17:42 2020/9/8
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return java.util.List<com.leyou.pojo.SpecParam>
     */
    @Override
    public List<SpecParam> queryParams(Long gid, Long cid, Boolean generic, Boolean searching) {
        // 查询参数
        Example example = new Example(SpecParam.class);
        Example.Criteria criteria = example.createCriteria();

        if (null != cid) {
            criteria.andEqualTo("cid", cid);
        }
        if (null != gid) {
            criteria.andEqualTo("groupId", gid);
        }

        if (null != generic) {
            criteria.andEqualTo("generic", generic);
        }
        if (null != searching) {
            criteria.andEqualTo("searching", searching);
        }
        criteria.andEqualTo("deleteMark", true);
        return specParamMapper.selectByExample(example);
    }



    /**
     * 新增规格参数
     * @date 10:42 2020/9/9
     * @param param
     * @return boolean
     */
    @Override
    public boolean saveParam(SpecParam param) {
        param.init();
        int result = specParamMapper.insertSelective(param);
        return result > 0 ? true : false;
    }

    /**
     * 修改规格参数
     * @date 10:42 2020/9/9
     * @param param
     * @return boolean
     */
    @Override
    public boolean updateParam(SpecParam param) {
        param.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        int result = specParamMapper.updateByPrimaryKeySelective(param);
        return result > 0 ? true : false;
    }

    /**
     * 删除规格参数
     * @date 10:42 2020/9/9
     * @param param
     * @return boolean
     */
    @Override
    public boolean deleteParam(SpecParam param) {
        param.setDeleteMark(0);
        int result = specParamMapper.updateByPrimaryKeySelective(param);
        return result > 0 ? true : false;
    }
}