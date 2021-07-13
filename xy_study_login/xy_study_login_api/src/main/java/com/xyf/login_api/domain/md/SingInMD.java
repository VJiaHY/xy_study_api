package com.xyf.login_api.domain.md;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.boot.test.autoconfigure.data.redis.AutoConfigureDataRedis;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class SingInMD implements Serializable {

    @NotBlank(message = "用户名不能为空")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String passWord;

    @NotBlank(message = "邮箱地址不能为空")
    @ApiModelProperty(value = "邮箱")
    @Email(message = "邮箱格式不正确")
    private String email;
}
