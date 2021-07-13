package com.xyf.login_api.domain;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * user对象 user
 *
 * @author birancloud
 * @date 2021-04-18
 */
@Data
@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@TableName("sys_user")
public class SysUser implements Serializable {

private static final long serialVersionUID=1L;


    //** 用户id *//*


    @TableId(value = "user_id")
    private Long userId;

    //** 用户名 *//*
    @Excel(name = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;

    //** 密码 *//*
    @Excel(name = "密码")
    @NotBlank(message = "密码不能为空")
    private String possWord;

    //** 用户类型 *//*
    @Excel(name = "用户类型")
    private String userType;

    //** 用户类型 *//*
    @NotBlank(message = "邮箱不能为空")
    @Excel(name = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
    //** 手机号 *//*
    @Excel(name = "手机号")
    @NotBlank(message = "手机号不能为空")
    private String userTel;

    //** 其他说明 *//*
    @Excel(name = "其他说明")
    private String userRemark;

    //**是否删除*//*
    private String isDel;

    //**创建时间*//*
    private LocalDate createTime;
    //**修改时间*//*
    private LocalDate updateTime;

    //**修改用户id*//*
    private Long updateUser;
    //**创建用户id*//*
    private Long createUser;


    //**用户状态*//*
    private String ban;
}
