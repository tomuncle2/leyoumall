package com.leyou.dto;

import com.leyou.pojo.SpecGroup;
import com.leyou.pojo.SpecParam;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author: 蔡迪
 * @date: 17:45 2020/9/20
 * @description: 获取规格参数组以及下面的规格参数
 */
@Data
public class SpecGroupDTO extends SpecGroup implements Serializable {

    /**组下规格参数*/
    private List<SpecParam> params;
}