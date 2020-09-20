package com.leyou.api;


import com.leyou.dto.SpecGroupDTO;
import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequestMapping("spec")
public interface SpecificationApi {

    /**
     * 查询规格参数组
     * @param cid
     * @return
     */
     @GetMapping("groups/{cid}")
     ResponseEntity<List<SpecGroup>> queryGroupsByCid(@PathVariable("cid") Long cid);

    /**
     * 查询规格参数
     * @param gid
     * @param cid
     * @param generic
     * @param searching
     * @return
     */
     @GetMapping("params")
     ResponseEntity<List<SpecParam>> queryParams(
            @RequestParam(value = "gid", required = false) Long gid,
            @RequestParam(value = "cid", required = false) Long cid,
            @RequestParam(value = "generic", required = false) Boolean generic,
            @RequestParam(value = "searching", required = false) Boolean searching
    );

    /**
     * 查询规格参数组，及组内参数
     * @param cid
     * @return SpecGroupDTO
     */
    @GetMapping("{cid}")
    List<SpecGroupDTO> querySpecsByCid(@PathVariable("cid") Long cid);

}
