package com.xyf.login_service.service.impl;

import cn.hutool.core.map.MapUtil;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyf.login_api.domain.SysUser;
import com.xyf.login_api.domain.md.LoginMD;
import com.xyf.login_api.domain.md.SingInMD;
import com.xyf.login_api.utils.MD5Utils;
import com.xyf.login_service.service.AccountService;
import com.xyf.login_service.service.SysUserService;
import com.xyf.result.R;
import com.xyf.service_base.handler.CustomExceptionHandler;
import com.xyf.service_shiro.utils.JwtUtils;

import org.apache.shiro.util.Assert;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;

import static com.xyf.result.CommonConstants.BAN_NORMAL;
import static com.xyf.result.CommonConstants.STATUS_NORMAL;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    private SysUserService userService;


    @Override
    public   R login(LoginMD loginMD, HttpServletResponse response) {
        SysUser user = userService.getOne(new QueryWrapper<SysUser>().eq("user_name", loginMD.getUserName()));
        Assert.notNull(user, "用户不存在");
        if(!user.getPossWord().equals(SecureUtil.md5(loginMD.getPassWord()))) {
            return R.failed("密码错误！");
        }
        String jwt = jwtUtils.generateToken(String.valueOf(user.getUserId()));
        System.out.println("11");
        response.setHeader("Authorization", jwt);
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        // 用户可以另一个接口
        return R.ok(MapUtil.builder()
                .put("id", user.getUserId())
                .put("username", user.getUserName())
                .put("email", user.getEmail())
                .map(),"loginMD"
        );
    }

    @Override
    public R sigIn(SingInMD singInMD) {
        /**
         * 判断
         */
        SysUser username = userService.getOne(new QueryWrapper<SysUser>().eq("user_name", singInMD.getUserName()));
        if(!StringUtils.isEmpty(username)){
            throw new CustomExceptionHandler(20001,"用户名已注册");
        }else{
            username = userService.getOne(new QueryWrapper<SysUser>().eq("email", singInMD.getEmail()));
            if(!StringUtils.isEmpty(username)){
                throw new CustomExceptionHandler(20001,"该邮箱已注册");
            }
        }

        SysUser user=new SysUser();



        BeanUtils.copyProperties(singInMD,user);//将singInMD中的所有相符属性，全部拷贝到user对象
        String saltMD5 = MD5Utils.getSaltMD5(singInMD.getPassWord());
        user.setPossWord(saltMD5);//加密密码

        user.setBan(BAN_NORMAL);//初始状态为1，表示正常
        user.setIsDel(STATUS_NORMAL);//初始逻辑删除为false就是0表示没有删除
        boolean save = userService.save(user);
        if(save){
            return R.ok();
        }
        throw new CustomExceptionHandler(20001,"注册失败");

    }

    @Override
    public R getCode() {
     return null;

    }
}
