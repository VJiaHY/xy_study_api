package com.xyf.login_service.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyf.login_api.domain.SysUser;
import org.apache.ibatis.annotations.Mapper;


/**
 * userMapper接口
 *
 * @author birancloud
 * @date 2021-04-18
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

}
