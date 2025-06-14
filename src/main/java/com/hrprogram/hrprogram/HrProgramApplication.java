package com.hrprogram.hrprogram;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableFeignClients(basePackages = "com.hrprogram.hrprogram.config")
public class HrProgramApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrProgramApplication.class, args);
    }

}
