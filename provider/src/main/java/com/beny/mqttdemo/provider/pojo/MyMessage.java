package com.beny.mqttdemo.provider.pojo;

import lombok.Data;

@Data
public class MyMessage {
    private String topic;

    private String content;
}
