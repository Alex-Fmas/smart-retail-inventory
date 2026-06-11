package com.retail.inventory.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")// 读取application.yml中的jwt节点
public class JwtProperties {

    private String secretKey;   // 密钥

    private Long expiration;    // 过期时间
}
