package com.beny.mqttdemo.consumer.controller;

import com.beny.mqttdemo.consumer.config.MqttConsumerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class TestController {
    @Resource
    private MqttConsumerConfig client;

    @Value("${spring.mqtt.client.id}")
    private String clientId;

    @RequestMapping("/connect")
    @ResponseBody
    public String connect() {
        client.connect();
        return clientId + "连接到服务器";
    }

    @RequestMapping("/disConnect")
    @ResponseBody
    public String disConnect() {
        client.disConnect();
        return clientId + "与服务器断开连接";
    }
}


