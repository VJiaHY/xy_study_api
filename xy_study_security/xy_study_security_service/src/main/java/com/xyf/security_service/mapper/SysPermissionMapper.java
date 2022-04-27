package com.xyf.security_service.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xyf.security_api.domain.sys.SysPermission;
import com.xyf.security_api.domain.sys.SysUser;
import org.apache.ibatis.annotations.Select;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @Author JiaHY
 * @Description //权限表
 * @Date 2022/4/15 20:42
 **/
@Mapper
public interface SysPermissionMapper extends BaseMapper<SysPermission> {

    /**
     * 根据用户id 获得权限列表
     * @param id
     * @return
     * JiaHY
     */
    @Select("select\n" +
            "            perms\n" +
            "            from\n" +
            "            sys_permission\n" +
            "            where\n" +
            "             id in \n" +
            "              (select\n" +
            "              permission_id\n" +
            "           from\n" +
            "               sys_role_permission\n" +
            "             where\n" +
            "              role_id in (\n" +
            "                   select\n" +
            "                    role_id\n" +
            "                from\n" +
            "                   sys_user_role\n" +
            "              where\n" +
            "                     user_id = #{id} \n" +
            "              ) \n" +
            "\t\n" +
            "             ) and\n" +
            "\t\t\t\t\t\t  del_flag = '0';")
    List<String> getPermsByUserId(long id);
}
