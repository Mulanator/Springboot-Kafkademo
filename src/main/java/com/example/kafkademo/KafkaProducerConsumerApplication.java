package com.example.kafkademo;

import com.example.kafkademo.service.KafkaProducer;
import com.example.kafkademo.service.MessageListener;
import com.example.kafkademo.service.MessageProducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class KafkaProducerConsumerApplication {

    public static void main(String[] args) throws Exception {

        ConfigurableApplicationContext context = SpringApplication.run(KafkaProducerConsumerApplication.class, args);

        MessageProducer producer = context.getBean(MessageProducer.class);
        MessageListener listener = context.getBean(MessageListener.class);
        KafkaProducer producer2 = context.getBean(KafkaProducer.class);
        producer2.send("test-topic", "Testnachricht");
        producer.sendMessage("Hello, World!");
        listener.latch.await(10, TimeUnit.SECONDS);
        producer.sendMessage("Hello, World 2nd!");
    }

    @Bean
    public MessageListener messageListener() {
        return new MessageListener();
    }

    @Bean
    public MessageProducer messageProducer() {
        return new MessageProducer();
    }

}
