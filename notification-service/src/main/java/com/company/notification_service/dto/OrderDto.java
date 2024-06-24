package com.company.notification_service.dto;

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
        OrderStatusDto orderStatus) {
}
