package com.nadou.consumer.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

import com.nadou.common.utils.QueueEnum;

/**
 *@ClassName RabbitMqConfig
 *@Description mq配置
 *@Author
 *@Date 2020/1/8 3:43 PM
 *@Version 1.0
 **/
@Configuration
public class RabbitMqConfig implements RabbitListenerConfigurer {
  @Override
  public void configureRabbitListeners(RabbitListenerEndpointRegistrar registrar) {
    registrar.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
  }

  @Bean
  MessageHandlerMethodFactory messageHandlerMethodFactory() {
    DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
    messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
    return messageHandlerMethodFactory;
  }

  @Bean
  public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
    return new MappingJackson2MessageConverter();
  }

  /***************************************定义交换机****************************************************/
  @Bean
  TopicExchange topicDemoExchange() {
    //配置路由器为Topic模式
    return (TopicExchange)ExchangeBuilder.
        topicExchange(QueueEnum.QUEUE_TOPIC_DEMO.getExchange())
        .durable(true)
        .build();
  }
  /***************************************定义队列****************************************************/

  @Bean
  public Queue topicDemoQueue() {
    return new Queue(QueueEnum.QUEUE_TOPIC_DEMO.getQname());
  }

  /***************************************定义binding****************************************************/
  @Bean
  Binding bindingTopicDemoExchange(TopicExchange topicDemoExchange, Queue topicDemoQueue) {
    // 配置该消息队列的  routingKey
    //topic.* 匹配 第一个.后面的单词    代表      一个    单词
    //比如 topic.asd 会被该消息队列接受 topic.asd.dsf不会被该消息队列接受
    //topic.# 匹配 所有.后面的单词     代表     任意    个      单词
    //比如 topic.asd 会被该消息队列接受 topic.asd.dsf也会被该消息队列接受
    return BindingBuilder
        .bind(topicDemoQueue)
        .to(topicDemoExchange)
        .with("haha.*");
  }


//  @Bean
//  DirectExchange logDirect() {
//    return (DirectExchange) ExchangeBuilder
//        .directExchange(QueueEnum.QUEUE_LOG.getExchange())
//        .durable(true)
//        .build();
//  }

//  @Bean
//  public Queue logQueue() {
//    return new Queue(QueueEnum.QUEUE_LOG.getQname());
//  }

//  @Bean
//  Binding logBinding(DirectExchange logDirect, Queue logQueue) {
//    return BindingBuilder
//        .bind(logQueue)
//        .to(logDirect)
//        .with(QueueEnum.QUEUE_LOG.getRouteKey());
//  }
}