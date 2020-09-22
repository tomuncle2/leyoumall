package com.leyou.config;

import com.leyou.common.constant.RabbitMqConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author: 蔡迪
 * @date: 20:51 2020/9/22
 * @description:
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Queue Queue1() {
        return new Queue(RabbitMqConstant.ITEM_QUEUE_GOODS_DELETE, true);
    }

    @Bean
    public Queue Queue2() {
        return new Queue(RabbitMqConstant.ITEM_QUEUE_GOODS_UPDATE, true);
    }

    @Bean
    public Queue Queue3() {
        return new Queue(RabbitMqConstant.ITEM_QUEUE_INDEX_DELETE, true);
    }

    @Bean
    public Queue Queue4() {
        return new Queue(RabbitMqConstant.ITEM_QUEUE_INDEX_UPDATE, true);
    }

    @Bean
    public TopicExchange topicExchange1() {
        return new TopicExchange(RabbitMqConstant.ITEM_EXCHANGE_TOPIC,true,false);
    }


    @Bean
    public Binding binding1() {
        return  BindingBuilder.bind(Queue1()).to(topicExchange1()).with(RabbitMqConstant.ITEM_TOPIC_KEY_DELETE);
    }


    @Bean
    public Binding binding2() {
        return  BindingBuilder.bind(Queue2()).to(topicExchange1()).with(RabbitMqConstant.ITEM_TOPIC_KEY_CHANGE);
    }

    @Bean
    public Binding binding3() {
        return  BindingBuilder.bind(Queue3()).to(topicExchange1()).with(RabbitMqConstant.ITEM_TOPIC_KEY_DELETE);
    }

    @Bean
    public Binding binding4() {
        return  BindingBuilder.bind(Queue4()).to(topicExchange1()).with(RabbitMqConstant.ITEM_TOPIC_KEY_CHANGE);
    }
}