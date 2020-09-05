package com.leyou.common.pojo;

import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author: 蔡迪
 * @date: 17:25 2020/9/5
 * @description: 基础pojo
 */
@Data
public class BasePojo implements Serializable {
    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

    /**
     * 保存操作人
     */
    @Column(name = "create_user")
    private String createUser;

    /**
     * 插入时间
     */
    @Column(name = "create_time")
    private Timestamp createTime;

    /**
     * 更新操作人
     */
    @Column(name = "update_user")
    private String updateUser;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Timestamp updateTime;

    /**
     * 是否删除;(1.可用，0删除，默认1)
     */
    @Column(name = "delete_mark")
    private Integer deleteMark;
}