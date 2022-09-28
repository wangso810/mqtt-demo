package com.beny.mqttdemo.provider;

import com.beny.mqttdemo.provider.config.MqttGateway;
import com.beny.mqttdemo.provider.pojo.MyMessage;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = {MqttDemoProviderApplication.class})
@RunWith(SpringRunner.class)
public class MqttTest {
    @Resource
    private MqttGateway mqttGateWay;

    @Test
    public void sendMessage() {
        // 发送消息到指定主题
        MyMessage myMessage = new MyMessage();
        myMessage.setTopic("demo");
        myMessage.setContent("hello world");

        mqttGateWay.sendToMqtt(myMessage.getTopic(), 1, myMessage.getContent());
        log.info("send topic: " + myMessage.getTopic() + ", message : " + myMessage.getContent());
    }
}
