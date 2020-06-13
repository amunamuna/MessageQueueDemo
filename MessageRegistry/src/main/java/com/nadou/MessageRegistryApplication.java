package com.nadou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class MessageRegistryApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageRegistryApplication.class, args);
    }

}
