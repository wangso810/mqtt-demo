package com.beny.mqttdemo.provider.controller;

import com.beny.mqttdemo.provider.config.MqttGateway;
import com.beny.mqttdemo.provider.pojo.MyMessage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class SendController {
    @Resource
    private MqttGateway mqttGateWay;

    @PostMapping("/sendMessage")
    public String send(@RequestBody MyMessage myMessage) {
        mqttGateWay.sendToMqtt(myMessage.getTopic(), 1, myMessage.getContent());
        return "send topic: " + myMessage.getTopic() + ", message : " + myMessage.getContent();
    }
}
