package com.company.payment_service.dto;

import com.company.payment_service.dto.enums.OrderStatusDto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderDto(
        String id,
        String userEmail,
        Long productId,
        Long quantity,
        BigDecimal totalAmount,
        LocalDateTime orderDate,
        String deliveryAddress,
        OrderStatusDto orderStatus
) {
}
