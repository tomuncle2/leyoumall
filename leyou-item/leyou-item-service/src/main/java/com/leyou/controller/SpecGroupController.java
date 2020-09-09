package com.leyou.controller;

import com.leyou.pojo.SpecGroup;
import com.leyou.service.SpecGroupService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:18 2020/9/8
 * @description: 规格组controller
 */
@RestController
@Slf4j
@RequestMapping("spec")
public class SpecGroupController {

    @Autowired
    private SpecGroupService specGroupService;

    /**
     * 获取分类下规格组
     * @date 10:16 2020/9/9
     * @param cid
     * @return org.springframework.http.ResponseEntity<java.util.List<com.leyou.pojo.SpecGroup>>
     */
    @GetMapping("groups/{cid}")
    public ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid) {
        List<SpecGroup> list = specGroupService.queryGroupsByCid(cid);
        return ResponseEntity.ok(list);
    }

    /**
     * 新增规格组
     * @date 10:16 2020/9/9
     * @param group
     * @return org.springframework.http.ResponseEntity
     */
    @PostMapping("group")
    public ResponseEntity saveGroup(@RequestBody SpecGroup group) {
        if(specGroupService.saveGroup(group)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

    /**
     * 修改规格组
     * @date 10:16 2020/9/9
     * @param group
     * @return org.springframework.http.ResponseEntity
     */
    @PutMapping("group")
    public ResponseEntity  updateGroup(@RequestBody SpecGroup group) {
        if(specGroupService.updateGroup(group)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

    /**
     * 删除规格组
     * @date 10:16 2020/9/9
     * @param id
     * @return org.springframework.http.ResponseEntity
     */
    @DeleteMapping("group/{id}")
    public ResponseEntity deleteGroup(@PathVariable("id") Long id) {
        SpecGroup group = new SpecGroup();
        group.setId(id);
        if(specGroupService.deleteGroup(group)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.FAILED_DEPENDENCY).build();
    }

}