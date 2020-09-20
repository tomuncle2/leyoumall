package com.leyou.pojo;


import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 16:54 2020/9/8
 * @description: 规格参数
 */
@Data
@Table(name = "tb_spec_param")
public class SpecParam extends BasePojo implements Serializable  {

    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**分类*/
    private Long cid;

    /**规格组id*/
    @Column(name = "group_id")
    private Long groupId;

    /**规格名*/
    private String name;

    /**是否数字类型规格 true:是 false:否  numeric和mysql关键字冲突，所以要修改名词*/
    @Column(name = "numerics")
    private Boolean numeric;

    /**数字类型规格的单位*/
    private String unit;

    /**数字类型规格的数字范围，可以用于搜索*/
    private String segments;

    /**是否是固定规格（一个spu里面，规格值唯一）*/
    private Boolean generic;

    /**是否用于搜索*/
    private Boolean searching;
}