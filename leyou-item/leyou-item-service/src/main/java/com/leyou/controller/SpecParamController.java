package com.leyou.controller;

import com.leyou.pojo.SpecParam;
import com.leyou.service.SpecParamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:17 2020/9/8
 * @description: 规格组，规格参数controler
 */
@RestController
@Slf4j
@RequestMapping("spec")
public class SpecParamController {

    @Autowired
    private SpecParamService specParamService;


    /**
     * gid查询规格参数(前台搜索和后台管理共用接口 实际开发不建议这么使用)
     * @date 11:05 2020/9/9
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.pojo.SpecParam>>
     */
    @GetMapping("params")
    public ResponseEntity<List<SpecParam>>  queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching) {
        // 查询参数
        List<SpecParam> list = specParamService.queryParams( gid,  cid,  generic,  searching);
        return ResponseEntity.ok(list);
    }

    /**
     * 新增规格参数
     * @date 11:05 2020/9/9
     * @param param
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping("param")
    public ResponseEntity saveParam(@RequestBody SpecParam param) {
        if(specParamService.saveParam(param)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

    /**
     * 修改规格参数
     * @date 11:05 2020/9/9
     * @param param
     * @return org.springframework.http.ResponseEntity
     */
    @PutMapping("param")
    public ResponseEntity updateOrSaveParam(@RequestBody SpecParam param) {
        if (null == param.getId()) {
            if (specParamService.saveParam(param)) {
                return ResponseEntity.ok().build();
            }
        } else {
            if (specParamService.updateParam(param)) {
                return ResponseEntity.ok().build();
            }
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

    /**
     * 删除规格参数
     * @date 11:05 2020/9/9
     * @param id
     * @return org.springframework.http.ResponseEntity
     */
    @DeleteMapping("param/{id}")
    public ResponseEntity  deleteParam(@PathVariable("id") Long id) {
        SpecParam param = new SpecParam();
        param.setId(id);
        if(specParamService.deleteParam(param)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }
}