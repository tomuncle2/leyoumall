package com.leyou.goods.listener;

import com.leyou.common.constant.RabbitMqConstant;
import com.leyou.goods.service.GoodsHtmlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author: 蔡迪
 * @date: 23:21 2020/9/22
 * @description: 商品信息消息队列消费
 */
@Component
@Slf4j
public class ItemRabbitmq {

    @Autowired
    private GoodsHtmlService service;

    @RabbitListener(queues = RabbitMqConstant.ITEM_QUEUE_GOODS_UPDATE)
    public void customeChange(Long id) {
        //
        log.warn("[goods-service]===RabbitListener:customeChange queue:{} message:{}",RabbitMqConstant.ITEM_QUEUE_GOODS_UPDATE,id);
        service.createHtml(id);
    }

    @RabbitListener(queues = RabbitMqConstant.ITEM_QUEUE_GOODS_DELETE)
    public void customeDelete(Long id) {
        log.warn("[goods-service]===RabbitListener:customeDelete queue:{} message:{}",RabbitMqConstant.ITEM_QUEUE_GOODS_DELETE,id);
        service.deleteHtml(id);
    }
}