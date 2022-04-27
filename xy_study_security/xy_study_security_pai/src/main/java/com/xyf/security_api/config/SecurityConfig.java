package com.xyf.security_api.config;

import com.xyf.security_api.filter.JwtAuthenticationTokenFilter;
import com.xyf.security_api.handler.AccessDeniedHandlerImpl;
import com.xyf.security_api.handler.AuthenticationEntryPointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @Author JiaHY
 * @Description //security 配置类
 * @Date 2022/4/16 20:11
 **/
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 加密
     *
     * @return JiaHY
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired //自定义认证过滤器
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable() //关闭csrf
              .sessionManagement()
              .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .authorizeRequests()
              //登录接口 可以匿名访问
              .antMatchers("/sys/login").anonymous()
              //除了上面的所有请求都需要认证
              .anyRequest()
              .authenticated()
              .and()
              //过滤器
              .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
              //异常处理
              .exceptionHandling()
              //认证异常
              .authenticationEntryPoint(authenticationEntryPoint)
              //权限异常
              .accessDeniedHandler(accessDeniedHandler).and()
              //允许跨域
              .cors();
    }

}
