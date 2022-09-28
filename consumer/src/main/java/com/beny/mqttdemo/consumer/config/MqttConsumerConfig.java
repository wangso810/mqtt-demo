package com.beny.mqttdemo.consumer.config;

import com.beny.mqttdemo.consumer.handler.MqttMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * 消费端配置
 *
 * @author wangso
 * @date 2022/09/27 18:27
 **/
@Slf4j
@Configuration
public class MqttConsumerConfig {

    @Resource
    private MqttProperty mqttProperty;

    @Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    @Bean
    public MqttPahoClientFactory inClientFactory() {
        //设置连接属性
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqttProperty.getUris());
        options.setUserName(mqttProperty.getUsername());
        options.setPassword(mqttProperty.getPassword().toCharArray());
        options.setConnectionTimeout(mqttProperty.getTimeout());
        options.setKeepAliveInterval(mqttProperty.getKeepalive());
        // 接受离线消息  告诉代理客户端是否要建立持久会话   false为建立持久会话
        options.setCleanSession(false);
        //设置断开后重新连接
        options.setAutomaticReconnect(true);
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageProducer inbound() {
        // Paho客户端消息驱动通道适配器，主要用来订阅主题  对inboundTopics主题进行监听
        //clientId 加后缀 不然会报retrying 不能重复
        MqttPahoMessageDrivenChannelAdapter adapter = new MqttPahoMessageDrivenChannelAdapter(mqttProperty.getClientId() + "_customer", inClientFactory(), mqttProperty.getTopics());
        adapter.setCompletionTimeout(5000);
        // Paho消息转换器
        DefaultPahoMessageConverter defaultPahoMessageConverter = new DefaultPahoMessageConverter();
        // 按字节接收消息
        // defaultPahoMessageConverter.setPayloadAsBytes(true);
        adapter.setConverter(defaultPahoMessageConverter);
        // 设置QoS
        adapter.setQos(mqttProperty.getQos());
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return new MqttMessageHandler();
    }
}
