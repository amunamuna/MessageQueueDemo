package com.nadou.consumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *@ClassName MessageConsumerApplication
 *@Description TODO
 *@Author nannan.zhang
 *@Date 2020/1/10 2:20 PM
 *@Version 1.0
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MessageConsumerApplication {
  public static void main(String[] args) {
    SpringApplication.run(MessageConsumerApplication.class, args);
  }
}
