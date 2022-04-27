package com.xyf.security_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_service.mapper.SysUserMapper;
import com.xyf.security_service.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @Author JiaHY
 * @Description //系统用户
 * @Date 2022/4/15 21:46
 **/
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

}
