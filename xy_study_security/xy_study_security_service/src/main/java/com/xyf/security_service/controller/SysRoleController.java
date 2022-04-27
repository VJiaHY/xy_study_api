package com.xyf.security_service.controller;

import com.xyf.result.R;
import com.xyf.security_service.service.SysRoleService;
import com.xyf.security_service.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author JiaHY
 * @Description //角色表
 * @Date 2022/4/16 19:39
 **/
@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class SysRoleController {
    private final SysRoleService sysRoleService;

}
