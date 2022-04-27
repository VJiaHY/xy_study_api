package com.xyf.security_service.controller;


import com.xyf.result.R;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_service.service.LoginUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * @Author JiaHY
 * @Description //登录授权
 * @Date 2022/4/16 20:00
 **/

@RestController
@RequestMapping("/sys")
@AllArgsConstructor
public class LoginUserController {
    private final LoginUserService loginUserService;
    /**
     * 登录接口
     * @param sysUser
     * @return
     * JiaHy
     */
    @PostMapping("/login")
    public R login(@RequestBody SysUser sysUser){
       return loginUserService.login(sysUser);
    }
    /**
     * 退出登录接口
     * @param
     * @return
     * JiaHy
     */
    @GetMapping("/logout")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public R logout(){
       return loginUserService.logout();
    }
}
