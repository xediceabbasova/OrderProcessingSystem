package com.company.payment_service.dto.request;

import com.company.payment_service.dto.enums.CurrencyDto;
import com.company.payment_service.dto.enums.PaymentMethodDto;

public record PaymentRequest(
        PaymentMethodDto paymentMethodDto,
        CurrencyDto currencyDto
) {
}
