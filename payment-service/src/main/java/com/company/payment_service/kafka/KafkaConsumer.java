package com.company.payment_service.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    @KafkaListener(topics = "order-topic", groupId = "group")
    public void consume(String message) {
        logger.info("Kafkadan gelen mesaj : {}", message);



    }
}
