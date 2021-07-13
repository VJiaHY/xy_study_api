package com.xyf.service_base.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常类
 * */

@Data//为类中成员变量自动生成get set toString
@AllArgsConstructor //自动生成有参构造方法，参数是所有参数
@NoArgsConstructor  //自动生成无参构造
public class CustomExceptionHandler extends RuntimeException{

    private Integer code;//状态码

    private String message;//异常信息
}
