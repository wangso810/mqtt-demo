package com.beny.mqttdemo.consumer.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

/**
 * MQTT消息处理
 *
 * @author wangso
 * @date 2022/09/28 15:06
 **/
@Slf4j
public class MqttMessageHandler implements MessageHandler {
    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        log.info("收到的完整消息为--->{}", message);
        log.info("----------------------");
        log.info("message:" + message.getPayload());
        log.info("Id:" + message.getHeaders().getId());
        log.info("receivedQos:" + message.getHeaders().get(MqttHeaders.RECEIVED_QOS));
        String topic = (String) message.getHeaders().get(MqttHeaders.RECEIVED_TOPIC);
        log.info("topic:" + topic);
        log.info("----------------------");
    }
}
