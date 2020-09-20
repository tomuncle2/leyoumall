package com.leyou.pojo;


import com.leyou.common.pojo.BasePojo;
import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author: 蔡迪
 * @date: 15:21 2020/9/10
 * @description:
 */
@Data
@Table(name = "tb_stock")
public class Stock extends BasePojo implements Serializable {
    @Id
    private Long skuId;

    /**库存总数*/
    private Integer stock;

    /**可参与秒杀的库存*/
    private Integer seckillStock;

    /**秒杀数量*/
    private Integer seckillTotal;

}