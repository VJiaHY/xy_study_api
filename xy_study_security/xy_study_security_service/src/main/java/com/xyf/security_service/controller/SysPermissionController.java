package com.xyf.security_service.controller;

import com.xyf.security_service.service.SysPermissionService;
import com.xyf.security_service.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JiaHY
 * @Description //ๆ้่กจ
 * @Date 2022/4/16 19:39
 **/
@RestController
@RequestMapping("/permission")
@AllArgsConstructor
public class SysPermissionController {
    private final SysPermissionService sysPermissionService;




}
