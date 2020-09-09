package com.leyou.pojo;

import com.leyou.common.pojo.BasePojo;
import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 15:12 2020/9/9
 * @description: sku
 */
@Data
@Table(name = "tb_sku")
public class Sku extends BasePojo implements Serializable {

}