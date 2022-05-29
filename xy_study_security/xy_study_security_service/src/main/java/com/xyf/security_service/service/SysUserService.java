package com.xyf.security_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyf.result.R;
import com.xyf.security_api.domain.sys.SysUser;
import org.springframework.validation.BindingResult;

/**
 * @Author JiaHY
 * @Description // 系统用户
 * @Date 2022/4/15 21:44
 **/
public interface SysUserService extends IService<SysUser> {

    /**
     * 获取邮箱验证码
     * @param sysUser
     * @return
     * JiaHY
     */
    R getEmailCode(SysUser sysUser);

    /**
     * 用户注册
     * @param sysUser
     * @return
     * JiaHY
     */
    R register(SysUser sysUser,BindingResult bindingResult);

    /**
     * 用户忘记密码验证码
     * @param sysUser
     * @return
     * JiaHY
     */
    R getResetCode(SysUser sysUser);

    /**
     * 用户重置密码
     * @param sysUser
     * @return
     * JiaHY
     */
    R restPassword(SysUser sysUser);
}
