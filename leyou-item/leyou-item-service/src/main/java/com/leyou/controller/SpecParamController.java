package com.leyou.controller;

import com.leyou.service.SpecParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蔡迪
 * @date: 17:17 2020/9/8
 * @description: 规格参数controler
 */
@RestController
@Slf4j
public class SpecParamController {

    @Autowired
    private SpecParamService specParamService;
}