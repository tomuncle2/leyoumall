package com.leyou.pojo;

import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 蔡迪
 * @date: 15:08 2020/9/9
 * @description: spu实体
 */
@Data
@Table(name="tb_spu")
public class Spu  extends BasePojo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**品牌*/
    private Long brandId;
    /**1级类目*/
    private Long cid1;
    /**2级类目*/
    private Long cid2;
    /**3级类目*/
    private Long cid3;
    /**标题*/
    private String title;
    /**子标题*/
    private String subTitle;
    /**是否上架*/
    private Boolean saleable;
    /**是否有效，逻辑删除用*/
    private String valid;
    /**最后修改时间*/
    private Timestamp lastUpdateTime;
}