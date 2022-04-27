package com.xyf.security_service.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xyf.security_api.domain.sys.SysPermission;
import com.xyf.security_api.domain.sys.SysRole;
import com.xyf.security_service.mapper.SysPermissionMapper;
import com.xyf.security_service.mapper.SysRoleMapper;
import com.xyf.security_service.service.SysPermissionService;
import com.xyf.security_service.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author JiaHY
 * @Description //权限表
 * @Date 2022/4/15 21:46
 **/
@Service
@AllArgsConstructor
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission> implements SysPermissionService {


    private final SysPermissionMapper sysPermissionMapper;
    /**
     * 根据用户id 获得权限列表
     * @param id
     * @return
     * JiaHY
     */
    public List<String> getPermsByUserId (long id){
        return sysPermissionMapper.getPermsByUserId(id);
    }

}
