package com.company.notification_service.kafka;

import com.company.notification_service.dto.OrderDto;
import com.company.notification_service.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final NotificationService notificationService;

    public KafkaConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "payment-topic", groupId = "notification-group")
    public void consume(String message) {
        logger.info("Kafkadan gelen mesaj : {}", message);

        OrderDto orderDto = convertMessageToOrderDto(message);
        if (orderDto != null) {
            notificationService.sendMessage(orderDto);
        }
    }

    private OrderDto convertMessageToOrderDto(String message) {
        try {
            return objectMapper.readValue(message, OrderDto.class);
        } catch (JsonProcessingException e) {
            logger.error("OrderDto-ya donusturme xetasi");
            throw new RuntimeException(e);
        }
    }
}
