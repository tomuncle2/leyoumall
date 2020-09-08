package com.leyou.controller;

import com.leyou.service.SpecGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: 蔡迪
 * @date: 17:18 2020/9/8
 * @description: 规格组controller
 */
@RestController
@Slf4j
public class SpecGroupController {

    @Autowired
    private SpecGroupService specGroupService;

}