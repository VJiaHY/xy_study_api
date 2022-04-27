package com.xyf.security_service.service;

import com.xyf.result.R;
import com.xyf.security_api.domain.sys.SysUser;

/**
 * @Author JiaHY
 * @Description //登录授权
 * @Date 2022/4/16 20:02
 **/
public interface LoginUserService {
    /**
     * 登录接口
     * @param sysUser
     * @return
     * JiaHy
     */
    R login(SysUser sysUser);

    /**
     * 退出登录接口
     * @param
     * @return
     * JiaHy
     */
    R logout();

}
