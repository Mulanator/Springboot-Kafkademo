package com.example.kafkademo.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import java.util.concurrent.CountDownLatch;

public class MessageListener {

    public CountDownLatch latch = new CountDownLatch(3);

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageListener.class);

    @KafkaListener(topics = "${app.topic}", containerFactory = "group1KafkaListenerContainerFactory")
    public void listenGroup1(String message) {
        LOGGER.info("MessageListener (group1) received payload: " + message);
        latch.countDown();
    }

//    @KafkaListener(topics = "${app.topic}", containerFactory = "group2KafkaListenerContainerFactory")
//    public void listenGroup2(String message) {
//       LOGGER.info("MessageListener (group2) received payload: " + message);
//        latch.countDown();
//    }

}