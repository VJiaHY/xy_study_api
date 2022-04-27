package com.xyf.security_service.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xyf.redis.RedisUtil;
import com.xyf.result.CommonConstants;
import com.xyf.security_api.domain.LoginUser;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_service.mapper.SysUserMapper;
import com.xyf.security_service.service.SysPermissionService;
import com.xyf.security_service.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @Author JiaHY
 * @Description //登录验证
 * @Date 2022/4/15 21:41
 **/
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final static Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private SysPermissionService sysPermissionService;
    /**
     * 获得用户信息 权限
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)){
            throw  new RuntimeException("请输入用户名!");
        }
        //获得用户信息
        SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUserName, username)
                .eq(SysUser::getDelF1ag, CommonConstants.BAN_NORMAL));
        if (Objects.isNull(sysUser)){
            throw  new RuntimeException("用户名密码错误,请重新输入!");
        }else {
            if (sysUser.getStatus().equals(CommonConstants.STATUS_PROHIBIT)){
                throw  new RuntimeException("当前用户已经被错定!");
            }
        }
        //获得用户权限信息
        List<String> permsByUserId = sysPermissionService.getPermsByUserId(sysUser.getId());
        //将用户信息存储redis
        boolean set = false;
        String sysUserJson = JSONObject.toJSONString(sysUser);
        try {
            set = redisUtil.set(CommonConstants.LOGIN_USER + sysUser.getId(), sysUserJson);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("用户信息存储Redis失败:" + e.getMessage());
        }
        if (!set){
            logger.error("用户信息存储Redis失败!");
            return new LoginUser();
        }else {
            return new LoginUser(sysUser,permsByUserId);
        }



    }
}
