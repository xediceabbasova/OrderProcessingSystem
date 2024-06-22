package com.company.payment_service.service;

import com.company.payment_service.dto.OrderDto;
import com.company.payment_service.dto.PaymentDto;
import com.company.payment_service.dto.converter.PaymentDtoConverter;
import com.company.payment_service.dto.enums.OrderStatusDto;
import com.company.payment_service.dto.request.PaymentRequest;
import com.company.payment_service.exception.OrderNotFoundException;
import com.company.payment_service.model.Payment;
import com.company.payment_service.model.enums.Currency;
import com.company.payment_service.model.enums.PaymentMethod;
import com.company.payment_service.model.enums.PaymentStatus;
import com.company.payment_service.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class PaymentService {

    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    private final PaymentRepository paymentRepository;
    private final PaymentDtoConverter paymentDtoConverter;
    private final ConcurrentHashMap<String, OrderDto> orderCache = new ConcurrentHashMap<>();

    public PaymentService(PaymentRepository paymentRepository, PaymentDtoConverter paymentDtoConverter) {
        this.paymentRepository = paymentRepository;
        this.paymentDtoConverter = paymentDtoConverter;
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

        Payment payment = new Payment(
                orderId,
                PaymentMethod.valueOf(paymentRequest.paymentMethodDto().name()),
                Currency.valueOf(paymentRequest.currencyDto().name())
        );

        logger.info("Payment processing simulated successfully.");

        payment.updatePaymentStatus(PaymentStatus.COMPLETED);
        paymentRepository.save(payment);

        OrderDto updatedOrderDto = new OrderDto(
                orderDto.id(),
                orderDto.userEmail(),
                orderDto.productId(),
                orderDto.quantity(),
                orderDto.totalAmount(),
                orderDto.orderDate(),
                orderDto.deliveryAddress(),
                OrderStatusDto.CONFIRMED
        );
        orderCache.put(orderId, updatedOrderDto);

        logger.info("Payment processing completed successfully for Order ID: {}", orderId);

        return paymentDtoConverter.convert(payment);
    }
}




