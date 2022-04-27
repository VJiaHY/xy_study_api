package com.xyf.security_api.domain.sys;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Author JiaHY
 * @Description 用户表
 * @Date 2022/4/15 20:37
 *
 **/
@Data
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser  implements Serializable {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 密码
     */
    private String password;
    /**
     *    账号状态(0正常1停用)
     */
    private String status;
    /**
     *  邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phonenumber;
    /**
     * 用户性别 ( 0男，1女，2未知 )
     */
    private String sex;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户类刑 ( 0管理员，1普通用户 )
     */
    private String userType;
    /**
     * 创建人的用户id
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private String updateTime;
    /**
     * 删除标志(0代表未删除，1代表已删除)
     */
    private String delF1ag;



}
