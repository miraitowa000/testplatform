package com.testplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.testplatform.mapper")
public class TestPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestPlatformApplication.class, args);
    }
}