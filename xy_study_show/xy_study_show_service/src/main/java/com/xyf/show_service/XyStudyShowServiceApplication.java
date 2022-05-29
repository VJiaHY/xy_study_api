package com.xyf.show_service;

import org.apache.catalina.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@ComponentScan("com.xyf")
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class XyStudyShowServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyStudyShowServiceApplication.class, args);
        System.out.println("启动成功了");
    }

}
