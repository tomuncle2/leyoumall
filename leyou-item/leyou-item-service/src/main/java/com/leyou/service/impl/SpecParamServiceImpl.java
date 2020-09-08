package com.leyou.service.impl;

import com.leyou.dao.SpecParamMapper;
import com.leyou.service.SpecParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author: 蔡迪
 * @date: 17:14 2020/9/8
 * @description: 规格实现类
 */
@Service
public class SpecParamServiceImpl implements SpecParamService {

    @Autowired
    private SpecParamMapper specParamMapper;
}