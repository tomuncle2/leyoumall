package com.leyou.pojo;

import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author: 蔡迪
 * @date: 15:11 2020/9/3
 * @description: 分类实体类
 */
@Data
@Table(name = "tb_category")
public class Category extends BasePojo {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long parentId;
    private Boolean isParent;
    /**
     * 排序指数，越小越靠前
     */
    private Integer sort;
    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }
}