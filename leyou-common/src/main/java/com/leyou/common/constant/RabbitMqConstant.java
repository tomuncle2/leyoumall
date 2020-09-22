package com.leyou.common.constant;

/**
 * @author: 蔡迪
 * @date: 21:21 2020/9/22
 * @description: rabbitmq常量
 */
public class RabbitMqConstant {

    /** 索引新增更新队列*/
    public static final String ITEM_QUEUE_INDEX_UPDATE = "item_index_queue_update" ;
    /** 索引删除队列*/
    public static final String ITEM_QUEUE_INDEX_DELETE = "item_index_queue_delete" ;

    /** 商品新增更新队列*/
    public static final String ITEM_QUEUE_GOODS_UPDATE = "item_goods_queue_update" ;
    /** 商品删除队列*/
    public static final String ITEM_QUEUE_GOODS_DELETE = "item_goods_queue_delete" ;

    /** 商品交换机*/
    public static final String ITEM_EXCHANGE_TOPIC = "item_exchange_topic" ;

    /** KEY UPDATE  or SAVE*/
    public static final String ITEM_TOPIC_KEY_CHANGE = "item_change.#" ;


    /** KEY SAVE*/
    public static final String ITEM_TOPIC_KEY_DELETE = "item_delete" ;
}