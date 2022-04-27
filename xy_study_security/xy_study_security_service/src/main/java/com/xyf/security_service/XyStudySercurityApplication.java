package com.xyf.security_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;


@SpringBootApplication
@ComponentScan({"com.xyf","com.xyf.security_service"})
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class XyStudySercurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyStudySercurityApplication.class,args);
    }
}
