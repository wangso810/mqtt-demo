package com.beny.mqttdemo.provider.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mqtt")
@Data
public class MqttProperty {

    /**
     * 服务器地址配置
     */
    private String[] uris;

    /**
     * clientId
     */
    private String clientId;

    /**
     * 话题
     */
    private String[] topics;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接超时时长
     */
    private Integer timeout;

    /**
     * keep Alive时间
     */
    private Integer keepalive;

    /**
     * QoS
     */
    private Integer qos;
}

