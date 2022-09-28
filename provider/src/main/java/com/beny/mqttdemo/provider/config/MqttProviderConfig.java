package com.beny.mqttdemo.provider.config;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

@Slf4j
@Configuration
public class MqttProviderConfig {

    @Resource
    private MqttProperty mqttProperty;

    @Bean
    public MessageChannel mqttOutBoundChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoClientFactory outClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        String[] uris = mqttProperty.getUris();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(uris);
        options.setUserName(mqttProperty.getUsername());
        options.setPassword(mqttProperty.getPassword().toCharArray());
        options.setConnectionTimeout(mqttProperty.getTimeout());
        options.setKeepAliveInterval(mqttProperty.getKeepalive());
        // 接受离线消息: 是否要建立持久会话,false为建立持久会话
        options.setCleanSession(false);
        // 设置断开后重新连接
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutBoundChannel")
    public MessageHandler mqttOutbound() {
        // 发送消息和消费消息Channel可以使用相同MqttPahoClientFactory
        // clientId 加后缀,不然会报retrying
        MqttPahoMessageHandler messageHandler = new MqttPahoMessageHandler(mqttProperty.getClientId() + "_producer", outClientFactory());
        // 如果设置成true，即异步，发送消息时将不会阻塞。
        messageHandler.setAsync(true);
        // 设置默认QoS
        messageHandler.setDefaultQos(mqttProperty.getQos());
        // Paho消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        // 发送默认按字节类型发送消息
        // defaultPahoMessageConverter.setPayloadAsBytes(true);
        messageHandler.setConverter(defaultPahoMessageConverter);
        return messageHandler;
    }
}
