package com.xyf.security_service.controller;

import com.xyf.result.R;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_service.service.SysUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 获取邮箱验证码
     * @param sysUser
     * @return
     * JiaHY
     */
    @PostMapping("/getEmailCode")
    public R getEmailCode(@RequestBody SysUser sysUser){
        return sysUserService.getEmailCode(sysUser);
    }

    /**
     * 用户注册
     * @param sysUser
     * @return
     * JiaHY
     */
    @PostMapping("/register")
    public R register(@RequestBody SysUser sysUser,BindingResult bindingResult){
        return sysUserService.register(sysUser,bindingResult);
    }

    /**
     * 用户忘记密码验证码
     * @param sysUser
     * @return
     * JiaHY
     */
    @PostMapping("/getResetCode")
    public R getResetCode(@RequestBody SysUser sysUser){
       return sysUserService.getResetCode(sysUser);
    }


    /**
     * 用户重置密码
     * @param sysUser
     * @return
     * JiaHY
     */
    @PostMapping("/restPassword")
    public R restPassword(@RequestBody SysUser sysUser){
        return sysUserService.restPassword(sysUser);
    }



}
