package com.zhupeiting.bisheproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.zhupeiting.bisheproject.mapper")
@EnableScheduling
public class BisheProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(BisheProjectApplication.class, args);
    }
}
