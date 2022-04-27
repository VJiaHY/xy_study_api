package com.xyf.security_service.controller;

import com.xyf.security_api.config.template.JwtConfigTemplate;
import com.xyf.security_service.XyStudySercurityApplication;
import com.xyf.security_service.mapper.SysPermissionMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = XyStudySercurityApplication.class)

public class LoginUserControllerTest {
    @Value("${jwt.secret}")
    private  String secret;
    @Autowired
    private JwtConfigTemplate jwtConfigTemplate;
    @Autowired
    private SysPermissionMapper sysPermissionMapper;

    @Test

    public void test01(){

        System.out.println(jwtConfigTemplate);
        System.out.println(secret);
    }
}