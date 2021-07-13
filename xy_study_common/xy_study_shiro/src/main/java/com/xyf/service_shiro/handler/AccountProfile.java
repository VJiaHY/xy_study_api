package com.xyf.service_shiro.handler;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountProfile implements Serializable {
    /**
     * 这是为了登录成功之后返回的一个用户信息的载体，
     */
    private Long id;
    private String username;
    private String avatar;
}
