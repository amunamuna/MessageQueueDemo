package com.nadou.certificate;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *@ClassName MessageCertificationApplication
 *@Description AS认证
 *@Author nannan.zhang
 *@Date 2020/6/12 16:55
 *@Version 1.0
 **/
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@EnableTransactionManagement
@MapperScan("com.nadou.certificate.web.mapper")
public class MessageCertificationApplication {
  public static void main(String[] args) {
    SpringApplication.run(MessageCertificationApplication.class, args);
  }
}
