package com.xyf.security_api.config.template;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author JiaHY
 * @Description //jwt 配置
 * @Date 2022/4/16 19:15
 **/
@Data
@Component
@ConfigurationProperties(prefix="jwt")
public class JwtConfigTemplate {
    /**
     * JWT令牌的有效时间，单位秒
     * - 默认2周
     */
    private Long expire;
    /**
     * 为JWT基础信息加密和解密的密钥
     * 在实际生产中通常不直接写在配置文件里面。而是通过应用的启动参数传递，并且需要定期修改。
     */
    private String secret;
}
