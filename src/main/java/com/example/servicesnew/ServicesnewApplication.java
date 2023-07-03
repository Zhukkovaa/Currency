package com.example.servicesnew;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicesnewApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicesnewApplication.class, args);
    }

}
