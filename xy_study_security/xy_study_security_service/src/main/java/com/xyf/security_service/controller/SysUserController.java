package com.xyf.security_service.controller;

import com.xyf.result.R;
import com.xyf.security_service.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JiaHY
 * @Description //用户表
 * @Date 2022/4/16 19:39
 **/
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class SysUserController {
    private final SysUserService sysUserService;


    @PostMapping("/select")
    @PreAuthorize("@MyEx.hasAuthority('sys:user:list')")
    public R getUserPage(){
       return R.ok(sysUserService.list());
    }

}
