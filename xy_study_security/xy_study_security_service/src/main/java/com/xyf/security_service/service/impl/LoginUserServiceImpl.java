package com.xyf.security_service.service.impl;

import com.netflix.client.http.HttpResponse;
import com.xyf.redis.RedisUtil;
import com.xyf.result.CommonConstants;
import com.xyf.result.R;
import com.xyf.security_api.domain.LoginUser;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_api.utils.JwtUtil;
import com.xyf.security_service.service.LoginUserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * @Author JiaHY
 * @Description //登录授权
 * @Date 2022/4/16 20:02
 **/
@Service
@AllArgsConstructor
public class LoginUserServiceImpl implements LoginUserService {
    private final static Logger logger = LoggerFactory.getLogger(LoginUserServiceImpl.class);

    private final AuthenticationManager authenticationManager;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 登录接口
     * @param sysUser
     * @return
     * JiaHy
     */
    @Override
    public R login(SysUser sysUser) {

        //首先验证登录是否通过
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        sysUser.getUserName(),
                        sysUser.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (Objects.isNull(authenticate)){
            return R.failed("用户名或密码错误!");
        }
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        if (Objects.isNull(principal)){
            return R.failed("用户信息验证失败!");
        }
        String userId = principal.getSysUser().getId().toString();

        //生成jwt 令牌
        String jwt = jwtUtil.generateToken(principal);
        HashMap<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token",jwt);
        //存储登录对象
        boolean set = false;
        try {
            set = redisUtil.set(CommonConstants.REDIS_TOKEN + userId, jwt);
        } catch (Exception e) {
           logger.error("redis存储失败:"+e.getMessage());
            return R.failed("用户信息存储失败!");
        }
        if (set){
            return R.ok(tokenMap);
        }else {
            return R.failed("用户信息存储失败!");
        }


    }

    /**
     * 退出登录接口
     * @param
     * @return
     * JiaHy
     */
    @Override
    public R logout() {
        //获得当前用户
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        //清除SecurityContextHolder 信息
        SecurityContextHolder.clearContext();
        //删除redis 信息
        redisUtil.del(CommonConstants.REDIS_TOKEN+principal.getSysUser().getId());
        return R.ok("退出登陆成功!");
    }
}
