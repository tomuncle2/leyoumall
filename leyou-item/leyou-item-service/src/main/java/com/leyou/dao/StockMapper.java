package com.leyou.dao;

import com.leyou.pojo.Stock;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author: 蔡迪
 * @date: 15:27 2020/9/10
 * @description: 库存mapper
 */
@Repository
public interface StockMapper extends Mapper<Stock> {

}