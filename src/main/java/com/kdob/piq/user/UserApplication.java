package com.kdob.piq.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.annotation.EnableKafka;

@EnableDiscoveryClient
@SpringBootApplication
@EnableKafka
public class UserApplication {

    static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
