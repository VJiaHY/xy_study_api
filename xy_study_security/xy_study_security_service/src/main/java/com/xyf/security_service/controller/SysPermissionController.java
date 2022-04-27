package com.xyf.security_service.controller;

import com.xyf.security_service.service.SysPermissionService;
import com.xyf.security_service.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JiaHY
 * @Description //权限表
 * @Date 2022/4/16 19:39
 **/
@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class SysPermissionController {
    private final SysPermissionService sysPermissionService;




}
