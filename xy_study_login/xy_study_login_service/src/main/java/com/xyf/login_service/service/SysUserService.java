package com.xyf.login_service.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xyf.login_api.domain.SysUser;
import com.xyf.result.R;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * userService接口
 *
 * @author birancloud
 * @date 2021-04-18
 */
public interface SysUserService extends IService<SysUser> {
    /**
     * 分页查询
     * @param page
     * @param sysUser
     * @return
     */
    Page fetchPage(Page page, SysUser sysUser);

    R importExcel(MultipartFile multipartFile) throws IOException;
}
