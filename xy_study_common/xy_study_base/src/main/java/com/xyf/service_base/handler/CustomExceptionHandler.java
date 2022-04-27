package com.xyf.service_base.handler;

import lombok.*;

/**
 * 自定义异常类
 * JiaHY
 * */

@Getter
@Setter//为类中成员变量自动生成get set toString
@AllArgsConstructor
@NoArgsConstructor  //自动生成无参构造
public class CustomExceptionHandler extends RuntimeException{

    private Integer code;//状态码

    private String message;//异常信息



    public CustomExceptionHandler(String message, int code) {
        super(message);
        this.message = message;
        this.code = code;
    }

    public CustomExceptionHandler(String message, int code, Throwable e) {
        super(message, e);
        this.message = message;
        this.code = code;
    }
    public CustomExceptionHandler(String message) {
        super(message);
        this.message = message;
    }
}
