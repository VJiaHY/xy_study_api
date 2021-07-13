package com.xyf.show_service;

import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
@ComponentScan("com.xyf")
public class XyStudyShowServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyStudyShowServiceApplication.class, args);
        System.out.println("启动成功了");
    }




}
