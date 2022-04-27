package com.xyf.security_api.expression;

import com.xyf.security_api.domain.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Author JiaHY
 * @Description //自定权限认证类
 * @Date 2022/4/26 16:59
 **/
@Component(value = "MyEx")
public class MyExpression  {

    public  Boolean hasAuthority(String  per){
        boolean b = false;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        if (principal.getPermsList().contains(per)){
            b = true;
        }
        return b;
    }
}
