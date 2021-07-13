package com.xyf.login_service.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xyf.login_api.domain.SysUser;
import com.xyf.login_service.service.SysUserService;
import com.xyf.result.R;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static com.xyf.result.CommonConstants.STATUS_DEL;


/**
 * SysUserController
 *
 * @author birancloud
 * @date 2021-04-18
 */
@RestController

@RequestMapping("/user" )
public class SysUserController {

    @Autowired
    private  SysUserService sysUserService;



    /**
     * 查询user列表
     */
    @GetMapping("/searchPage")
    public R list(Page page, SysUser sysUser) {
        return R.ok(sysUserService.fetchPage(page, sysUser));
    }

    /**
     * 获取user详细信息
     */
    @GetMapping(value = "/{userId}" )
    public R getInfo(@PathVariable("userId" ) Long userId) {
        return R.ok(sysUserService.getById(userId));
    }

    /**
     * 新增user
     */
    @PostMapping
    public R add(@RequestBody SysUser sysUser) {
        if (sysUserService.save(sysUser)){
            return R.ok("新增成功");
        }else {
            return R.failed("新增失败");
        }
    }

    /**
     * 修改user
     */
    @PutMapping
    public R edit(@RequestBody SysUser sysUser) {

        if (sysUserService.updateById(sysUser)){
            return R.ok("修改成功");
        }else {
            return R.failed("修改失败");
        }
    }

    /**
     * 删除user
     */
    @DeleteMapping("delObj/{id}" )
    public R remove(@PathVariable Long id) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(id);
        sysUser.setIsDel(STATUS_DEL);
        if (sysUserService.updateById(sysUser)){
            return R.ok("删除成功");
        }else {
            return R.failed("删除失败");
        }
    }

    @PostMapping("/importExcel")
    public R importExcel(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        return  sysUserService.importExcel(multipartFile);
    }



    /**
     * 查询user列表
     */
    @GetMapping("/find")
    public R find() {
        return R.ok("啊啊啊啊啊");
    }




}
