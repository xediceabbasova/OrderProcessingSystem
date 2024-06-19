package com.company.order_service.dto.request;

import java.math.BigDecimal;

public record OrderRequest(
        String userEmail,
        Long productId,
        Long quantity,
        BigDecimal totalAmount,
        String deliveryAddress
) {
}
