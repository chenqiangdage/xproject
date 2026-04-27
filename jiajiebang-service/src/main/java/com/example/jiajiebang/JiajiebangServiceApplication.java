package com.example.jiajiebang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.example.jiajiebang.mapper")
public class JiajiebangServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiajiebangServiceApplication.class, args);
    }
}
