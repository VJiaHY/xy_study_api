package com.xyf.show_pai.config.template;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="spring.datasource")
public class DatasourceTemplate {
    private String url;
    private String username;
    private String password;
}
