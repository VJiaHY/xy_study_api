package com.xyf.eureka_server;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class XyStudyEruekaApplication {
    public static void main(String[] args) {
        SpringApplication.run(XyStudyEruekaApplication.class,args);
    }
}
