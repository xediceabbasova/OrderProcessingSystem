package com.company.payment_service.dto.converter;

import com.company.payment_service.dto.enums.PaymentMethodDto;
import com.company.payment_service.model.enums.PaymentMethod;

public class PaymentMethodDtoConverter {

    public static PaymentMethodDto convertToDto(PaymentMethod paymentMethod) {
        return switch (paymentMethod) {
            case CREDIT_CARD -> PaymentMethodDto.CREDIT_CARD;
            case DEBIT_CARD -> PaymentMethodDto.DEBIT_CARD;
            case BANK_TRANSFER -> PaymentMethodDto.BANK_TRANSFER;
            case PAYPAL -> PaymentMethodDto.PAYPAL;
            case GOOGLE_PAY -> PaymentMethodDto.GOOGLE_PAY;
            case APPLE_PAY -> PaymentMethodDto.APPLE_PAY;
            case OTHER -> PaymentMethodDto.OTHER;
        };
    }
}
