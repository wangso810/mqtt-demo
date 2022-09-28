package com.beny.mqttdemo.provider.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutBoundChannel")
public interface MqttGateway {
    /**
     * 定义重载方法，用于消息发送
     */
    void sendToMqtt(String payload);

    /**
     * 指定topic进行消息发送
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, String payload);

    /**
     * 指定topic和qos进行消息发送
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, String payload);

    /**
     * 指定topic和qos进行消息发送
     */
    void sendToMqtt(@Header(MqttHeaders.TOPIC) String topic, @Header(MqttHeaders.QOS) int qos, byte[] payload);
}

