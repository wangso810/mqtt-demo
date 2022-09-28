package com.beny.mqttdemo.consumer;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 用服类
 *
 * @author wangso
 * @date 2022/09/27 14:13
 **/
@SpringBootApplication
public class MqttDemoConsumerApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(MqttDemoConsumerApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
