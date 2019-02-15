package com.userservice2.userdemo2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UserDemo2Application {

    public static void main(String[] args) {
        SpringApplication.run(UserDemo2Application.class, args);
    }

}

