package com.xyf.login_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.xyf")
//@EnableEurekaClient
public class XyStudyLoginServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyStudyLoginServiceApplication.class,args);
    }
}
