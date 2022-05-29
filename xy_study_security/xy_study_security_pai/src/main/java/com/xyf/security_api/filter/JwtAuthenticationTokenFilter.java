package com.xyf.security_api.filter;

import com.xyf.redis.RedisUtil;
import com.xyf.result.CommonConstants;
import com.xyf.security_api.domain.LoginUser;
import com.xyf.security_api.utils.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Objects;

/**
 * @Author JiaHY
 * @Description //jwt 过滤器
 * @Date 2022/4/17 16:25
 **/
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    Logger logger = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //首先获取token
        //如果没有token 放行
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }
        //校验token
        //如果token检验失败  放行
        LoginUser element = jwtUtil.getElement(token);
        if (Objects.isNull(element)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            logger.error("token非法:" + token);
            return;
        }
        Long id = element.getSysUser().getId();
        if (0 == id && null == id) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            logger.error("token非法:" + token);
            return;
        }
        //redis获取用户信息
        //如果redis没有当前用户信息  放行
        boolean b = redisUtil.hasKey(CommonConstants.REDIS_TOKEN + id);
        if (b) {
            String redisToken = (String) redisUtil.get(CommonConstants.REDIS_TOKEN + id);
            if (token.equals(redisToken)) {
                //将用户信息存储到SercurityContextHolder
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(
                                element,
                                null,
                                element.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                filterChain.doFilter(httpServletRequest, httpServletResponse);
            } else {
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                logger.error("Redis Token 不一致,id:" + element.getSysUser().getId());
                return;
            }
        } else {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            logger.error("Redis缓存失效,id:" + element.getSysUser().getId());
            return;
        }
    }
}
