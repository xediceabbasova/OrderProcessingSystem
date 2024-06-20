package com.company.payment_service.dto.converter;

import com.company.payment_service.dto.enums.PaymentStatusDto;
import com.company.payment_service.model.enums.PaymentStatus;

public class PaymentStatusDtoConverter {

    public static PaymentStatusDto convertToDto(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            case PENDING -> PaymentStatusDto.PENDING;
            case COMPLETED -> PaymentStatusDto.COMPLETED;
            case FAILED -> PaymentStatusDto.FAILED;
        };
    }
}
