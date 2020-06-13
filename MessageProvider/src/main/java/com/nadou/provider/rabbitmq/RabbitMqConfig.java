package com.nadou.provider.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nadou.common.utils.QueueEnum;

/**
 *@ClassName RabbitMqConfig
 *@Description TODO
 *@Author nannan.zhang
 *@Date 2020/1/8 3:43 PM
 *@Version 1.0
 **/
@Configuration
public class RabbitMqConfig {

  @Bean
  public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
    final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
    rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
    return rabbitTemplate;
  }

  @Bean
  public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
    return new Jackson2JsonMessageConverter();
  }
  /***************************************定义交换机****************************************************/

  @Bean
  DirectExchange logDirect() {
    return (DirectExchange) ExchangeBuilder
        .directExchange(QueueEnum.QUEUE_LOG.getExchange())
        .durable(true)
        .build();
  }
//  @Bean
//  DirectExchange ordinaryDirect() {
//    return (DirectExchange) ExchangeBuilder
//        .directExchange(QueueEnum.QUEUE_ORDINARY.getExchange())
//        .durable(true)
//        .build();
//  }
//
//  //死信交换机
//  @Bean
//  DirectExchange deadLetterDirect() {
//    return (DirectExchange) ExchangeBuilder
//        .directExchange(QueueEnum.QUEUE_DEAD_LETTER.getExchange())
//        .durable(true)
//        .build();
//  }
  /***************************************定义队列****************************************************/

  @Bean
  public Queue logQueue() {
    return new Queue(QueueEnum.QUEUE_LOG.getQname());
  }

//  @Bean
//  public Queue ordinaryQueue() {
//    return QueueBuilder
//        .durable(QueueEnum.QUEUE_ORDINARY.getQname())
//        .withArgument("x-dead-letter-exchange", QueueEnum.QUEUE_DEAD_LETTER.getExchange())//到期后转发的交换机
//        .withArgument("x-dead-letter-routing-key", QueueEnum.QUEUE_DEAD_LETTER.getRouteKey())//到期后转发的路由键
//        .build();
//  }
//  //死信队列
//  @Bean
//  public Queue deadLetterQueue() {
//    return new Queue(QueueEnum.QUEUE_DEAD_LETTER.getQname());
//  }

  /***************************************定义binding****************************************************/
  @Bean
  Binding logBinding(DirectExchange logDirect,Queue logQueue){
    return BindingBuilder
        .bind(logQueue)
        .to(logDirect)
        .with(QueueEnum.QUEUE_LOG.getRouteKey());
  }
//  @Bean
//  Binding ordinaryBinding(DirectExchange ordinaryDirect,Queue ordinaryQueue){
//    return BindingBuilder
//        .bind(ordinaryQueue)
//        .to(ordinaryDirect)
//        .with(QueueEnum.QUEUE_ORDINARY.getRouteKey());
//  }
//
//  @Bean
//  Binding deadLetterBinding(DirectExchange deadLetterDirect,Queue deadLetterQueue){
//    return BindingBuilder
//        .bind(deadLetterQueue)
//        .to(deadLetterDirect)
//        .with(QueueEnum.QUEUE_DEAD_LETTER.getRouteKey());
//  }
}