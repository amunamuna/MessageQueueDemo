package com.nadou.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 *@ClassName MessageProviderApplication
 *@Description TODO
 *@Author
 *@Date 2020/1/10 2:20 PM
 *@Version 1.0
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class MessageProviderApplication {
  public static void main(String[] args) {
    SpringApplication.run(MessageProviderApplication.class, args);
  }
}
