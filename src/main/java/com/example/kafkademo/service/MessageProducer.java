package com.example.kafkademo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public  class MessageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value = "${app.topic}")
    private String topicName;

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProducer.class);

    public void sendMessage(String message) {

        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topicName, message);

        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOGGER.info("MessageProducer sent message=[" + message + "] with offset=[" + result.getRecordMetadata()
                        .offset() + "] and topic = " + topicName);
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.error("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        });
    }

}