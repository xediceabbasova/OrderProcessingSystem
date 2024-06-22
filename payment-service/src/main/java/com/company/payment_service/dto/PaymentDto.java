package com.company.payment_service.dto;

import com.company.payment_service.dto.enums.CurrencyDto;
import com.company.payment_service.dto.enums.PaymentMethodDto;
import com.company.payment_service.dto.enums.PaymentStatusDto;

import java.time.LocalDateTime;

public record PaymentDto(
        String id,
        String orderId,
        LocalDateTime paymentDate,
        PaymentMethodDto paymentMethod,
        PaymentStatusDto paymentStatus,
        CurrencyDto currency
) {
}
