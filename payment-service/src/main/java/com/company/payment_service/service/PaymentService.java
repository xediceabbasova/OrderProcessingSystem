package com.company.payment_service.service;

import com.company.payment_service.client.OrderServiceClient;
import com.company.payment_service.dto.OrderDto;
import com.company.payment_service.dto.PaymentDto;
import com.company.payment_service.dto.converter.PaymentDtoConverter;
import com.company.payment_service.dto.enums.OrderStatusDto;
import com.company.payment_service.dto.request.PaymentRequest;
import com.company.payment_service.exception.OrderNotFoundException;
import com.company.payment_service.kafka.KafkaProducer;
import com.company.payment_service.model.Payment;
import com.company.payment_service.model.enums.Currency;
import com.company.payment_service.model.enums.PaymentMethod;
import com.company.payment_service.model.enums.PaymentStatus;
import com.company.payment_service.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentDtoConverter paymentDtoConverter;
    private final KafkaProducer kafkaProducer;
    private final OrderServiceClient orderServiceClient;

    private final ConcurrentHashMap<String, OrderDto> orderCache = new ConcurrentHashMap<>();
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public PaymentService(PaymentRepository paymentRepository, PaymentDtoConverter paymentDtoConverter, KafkaProducer kafkaProducer, OrderServiceClient orderServiceClient) {
        this.paymentRepository = paymentRepository;
        this.paymentDtoConverter = paymentDtoConverter;
        this.kafkaProducer = kafkaProducer;
        this.orderServiceClient = orderServiceClient;
    }

    public void cacheOrder(OrderDto orderDto) {
        orderCache.put(orderDto.id(), orderDto);
    }

    public PaymentDto processPayment(String orderId, PaymentRequest paymentRequest) {
        OrderDto orderDto = orderCache.get(orderId);

        if (orderDto == null) {
            logger.error("Order ID not found in cache: {}", orderId);
            throw new OrderNotFoundException("Order ID not found in cache: " + orderId);
        }

        Payment payment = createPayment(orderId, paymentRequest);

        try {
            logger.info("Payment processing simulated successfully.");

            payment.updatePaymentStatus(PaymentStatus.COMPLETED);
            orderServiceClient.confirmOrder(orderId);
            orderDto = orderDto.updateOrderStatus(OrderStatusDto.CONFIRMED);

            logger.info("Payment processing completed successfully for Order ID: {}", orderId);
        } catch (Exception e) {
            logger.error("Payment processing failed for Order ID: {}", orderId, e);

            payment.updatePaymentStatus(PaymentStatus.FAILED);
            orderServiceClient.cancelOrder(orderId);
            orderDto = orderDto.updateOrderStatus(OrderStatusDto.CANCELLED);

            logger.info("Order has been cancelled for Order ID: {}", orderId);
        }
        paymentRepository.save(payment);

        String message;
        try {
            message = objectMapper.writeValueAsString(orderDto);
        } catch (
                JsonProcessingException e) {
            throw new RuntimeException("Error converting OrderDto to JSON", e);
        }
        kafkaProducer.sendMessage(message);

        return paymentDtoConverter.convert(payment);
    }

    private Payment createPayment(String orderId, PaymentRequest paymentRequest) {
        return new Payment(
                orderId,
                PaymentMethod.valueOf(paymentRequest.paymentMethodDto().name()),
                Currency.valueOf(paymentRequest.currencyDto().name())
        );
    }

}