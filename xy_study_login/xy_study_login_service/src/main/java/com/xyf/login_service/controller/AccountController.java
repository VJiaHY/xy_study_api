package com.xyf.login_service.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.SecureUtil;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyf.login_api.domain.SysUser;
import com.xyf.login_api.domain.md.LoginMD;
import com.xyf.login_api.domain.md.SingInMD;
import com.xyf.login_service.service.AccountService;
import com.xyf.login_service.service.SysUserService;
import com.xyf.result.R;
import com.xyf.service_shiro.utils.JwtUtils;
import org.apache.shiro.SecurityUtils;

import org.apache.shiro.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/xy/account")
public class AccountController {


    @Autowired
    private AccountService accountService;
    /**
     *
     * @param loginMD   登陆实体对象
     * @param response 我们做登陆，需要将jwt放在header中，这就需要HttpServletResponse
     * @return
     */
    @CrossOrigin
    @PostMapping("/login")
    public R login(@Validated @RequestBody LoginMD loginMD, HttpServletResponse response) {
        return accountService.login(loginMD,response);
    }

    // 退出
    @GetMapping("/logout")
    //@RequiresAuthentication//必须认证通过才能退出
    public R logout() {
        SecurityUtils.getSubject().logout();
        return R.ok();
    }

    /**
     * 注册
     * @param singInMD
     * @return
     */
    @PostMapping("signIn")
    public R sigIn(@Validated @RequestBody SingInMD singInMD){
        return accountService.sigIn(singInMD);
    }


    /**
     * 获取验证码
     * @return
     */
    @GetMapping("/getCode")
    public R getCode(){
        return accountService.getCode();
    }

}
