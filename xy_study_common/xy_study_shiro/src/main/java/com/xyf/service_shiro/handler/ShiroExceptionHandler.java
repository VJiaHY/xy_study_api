package com.xyf.service_shiro.handler;


import com.xyf.result.R;
import com.xyf.service_base.handler.MyExceptionHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@Slf4j
@Component
public class ShiroExceptionHandler extends MyExceptionHandler {



    // 捕捉shiro的异常
    @ResponseBody
    @ExceptionHandler(ShiroException.class)
    public R handle401(org.apache.shiro.ShiroException e) {

        return R.failed(401,"恭喜"+e.getMessage());
    }

    /**
     * 处理Assert的异常
     */
    @ResponseBody
    @ExceptionHandler(value = IllegalArgumentException.class)
    public R handler(IllegalArgumentException e) throws IOException {
        return R.failed(e.getMessage());
    }
    /**
     * @Validated 校验错误异常处理
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public R handler(MethodArgumentNotValidException e) throws IOException {
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
         return R.failed(objectError.getDefaultMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = RuntimeException.class)
    public R handler(RuntimeException e) throws IOException {
        //此处可添加邮箱 暂缓...........
        return R.failed("恭喜您发现一个Bug:"+e.getMessage());
    }
}
