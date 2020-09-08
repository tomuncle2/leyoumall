package com.leyou.pojo;

import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 16:54 2020/9/8
 * @description: 规格参数组
 */
@Data
@Table(name = "tb_spec_group")
public class SpecGroup extends BasePojo implements Serializable {

    /**id*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**分类*/
    private Long cid;
    /**规格组名称*/
    private String name;
}