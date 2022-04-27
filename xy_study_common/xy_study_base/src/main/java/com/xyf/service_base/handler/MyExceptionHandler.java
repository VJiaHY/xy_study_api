package com.xyf.service_base.handler;


import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理、全局数据绑定、全局数据预处理
 * JiaHY
 */
@ControllerAdvice
public class MyExceptionHandler  {


    /**
     * 处理自定义异常
     * JiaHY
     */
    @ExceptionHandler(CustomExceptionHandler.class)//表示此方法只对我们自己定义的这个异常有效
    @ResponseBody
    public CustomExceptionHandler customError(CustomExceptionHandler e){//参数就是我们自定义异常类的对象
        e.printStackTrace();//父类提供的方法
        //邮箱处理暂缓
        return new CustomExceptionHandler("恭喜你找到一个Bug",1,e);//将统一返回结果的状态码和提示信息，换为自定以异常的
    }
}
