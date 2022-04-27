package com.xyf.security_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyf.security_api.domain.sys.SysRole;
import com.xyf.security_api.domain.sys.SysUser;
import com.xyf.security_service.mapper.SysRoleMapper;
import com.xyf.security_service.mapper.SysUserMapper;
import com.xyf.security_service.service.SysRoleService;
import com.xyf.security_service.service.SysUserService;
import org.springframework.stereotype.Service;

/**
 * @Author JiaHY
 * @Description //角色表
 * @Date 2022/4/15 21:46
 **/
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

}
