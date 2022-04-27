package com.xyf.security_service.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xyf.security_api.domain.sys.SysPermission;
import com.xyf.security_api.domain.sys.SysRole;

import java.util.List;

/**
 * @Author JiaHY
 * @Description // 权限表
 * @Date 2022/4/15 21:44
 **/
public interface SysPermissionService extends IService<SysPermission> {

    /**
     * 根据用户id 获得权限列表
     * @param id
     * @return
     * JiaHY
     */
    List<String> getPermsByUserId (long id);
}
