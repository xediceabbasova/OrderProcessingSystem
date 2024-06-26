package com.company.payment_service.kafka;

import com.company.payment_service.dto.OrderDto;
import com.company.payment_service.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    private final PaymentService paymentService;

    public KafkaConsumer(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void consume(String message) {
        logger.info("Kafkadan gelen mesaj : {}", message);

        OrderDto orderDto = convertMessageToOrderDto(message);
        if (orderDto != null) {
            paymentService.cacheOrder(orderDto);
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
